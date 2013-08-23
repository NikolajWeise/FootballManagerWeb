package de.weise.fm.web.components.layout;

import java.util.Iterator;
import java.util.List;

import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.MouseEventDetails.MouseButton;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Image;
import com.vaadin.ui.Layout;

import de.weise.fm.web.components.js.buttonoption.ButtonOption;
import de.weise.fm.web.components.js.canvas.Canvas;
import de.weise.fm.web.model.play.Action;
import de.weise.fm.web.model.play.ActionType;
import de.weise.fm.web.model.play.FieldPosition;
import de.weise.fm.web.model.play.Formation;
import de.weise.fm.web.model.play.Play;
import de.weise.fm.web.model.play.Play.PlayType;
import de.weise.fm.web.model.play.PositionWrapper;

public class PlayFormationField extends FormationField {

    protected Canvas canvas;
    protected ButtonOption<PlayType> optionPlayType;
    protected ButtonOption<ActionType> optionActionTypes;
    protected CheckBox chkPrimaryAction;
    protected Play currentPlay;

    /** indicates if we are in action adding mode (<tt>true</tt>) or image selecting mode (<tt>false</tt>) */
    private boolean addNewAction = false;
    /** indicates if we've automatically drawed a route after selecting an ActionType */
    private boolean addedAutoAction = false;

    protected Image imgSelectedPosition;
    protected ThemeResource resImgBackground;
    protected ThemeResource resImgBackgroundSelected;

    /**
     * Creates a <tt>PlayFormationField</tt> with the given dimensions. Also creates a {@link Canvas} laying on the
     * field at (0,0) with same height and width as the field.
     */
    public PlayFormationField(int height, int width) {
        super(height, width);

        resImgBackground = new ThemeResource("img/positions/circle.png");
        resImgBackgroundSelected = new ThemeResource("img/positions/circle_yellow.png");

        canvas = new Canvas(height, width);
    }

    @Override
    protected void initComponents(Layout layout) {
        layout.addComponent(field);
        field.addLayoutClickListener(new FieldClickListener());

        optionPlayType = new ButtonOption<>();
        optionPlayType.addItems(PlayType.getOffenseTypes());
        optionPlayType.addStyleName("btn-default");
        optionPlayType.addStyleName("equalwidth");
        optionPlayType.addStyleName("spacing");
        optionPlayType.addValueChangeListener(new PlayTypeChangeListener());

        optionActionTypes = new ButtonOption<>();
        optionActionTypes.setWidth("300px");
        optionActionTypes.addStyleName("btn-primary");
        optionActionTypes.addStyleName("equalwidth");
        optionActionTypes.addValueChangeListener(new ActionTypeChangeListener());

        chkPrimaryAction = new CheckBox("Primary receiver", false); // TODO ugly checkbox, draw something else?
        chkPrimaryAction.setVisible(false);

        layout.addComponents(optionPlayType, optionActionTypes, chkPrimaryAction);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        optionPlayType.setVisible(enabled);
    }

    @Override
    public void showFormation(Formation formation) {
        super.showFormation(formation);

        // re-add canvas because showFormation() removes all children
        field.addComponent(canvas, "top:0px;left:0px");
    }

    @Override
    protected void createPositionImage(PositionWrapper pw, String cssPosition) {
        Image img = new Image(null, new ThemeResource("img/positions/" + pw.getPosition().name() + ".png"));
        img.addStyleName("notmovable");

        // this image is the selected one (imgSelectedPosition)
        Image imgCircle = new Image(null, resImgBackground);
        imgCircle.setData(pw);

        field.addComponent(imgCircle, cssPosition);
        field.addComponent(img, cssPosition);
    }

    /**
     * Shows the given play in {@link Canvas}. This means to show a new play not to refresh the current one.
     * <p>
     * Before setting the play to canvas all height and width coordinates of actions are being transformed to layout
     * coordinates. This means the canvas will not work with the given play but on a clone with layout coordinates
     * instead of logical coordinates.
     * <p>
     * Validates the play to add the "playnotvalid" css-style if it is not valid.
     *
     * @param play The play to show.
     */
    public void showPlay(Play play) {
        showPlay(play, true);
        optionPlayType.setValue(play.getPlayType());
    }

    /**
     * @param play The play to show.
     * @param loadNew <tt>true</tt> if loading a new play, <tt>false</tt> if the current play is shown already and is
     *        just refreshed.
     * @see #showPlay(Play)
     */
    private void showPlay(Play play, boolean loadNew) {
        field.removeStyleName("playnotvalid");
        this.currentPlay = play;

        // prepare height and width to pixel coordinates for html canvas element
        Play clone = play.clone(); // clone the play in order to not change the coordinates at the internal play object
        for(PositionWrapper pw : clone.getActions().keySet()) {

            List<Action> actions = clone.getActions().get(pw);
            if(actions != null) {
                for(Action action : actions) {
                    int height = action.getDirection().getHeight();
                    if(!clone.isDefensePlay()) {
                        height *= -1; // negate when offense
                    }
                    height = convertHeightToLayoutPosition(height);
                    int width = convertWidthToLayoutPosition(action.getDirection().getWidth());
                    action.getDirection().setHeight(height + 15);
                    action.getDirection().setWidth(width + 15);
                }
            }

            int height = pw.getFieldPosition().getHeight();
            if(!clone.isDefensePlay()) {
                height *= -1; // negate when offense
            }
            height = convertHeightToLayoutPosition(height);
            int width = convertWidthToLayoutPosition(pw.getFieldPosition().getWidth());
            pw.getFieldPosition().setHeight(height + 15);
            pw.getFieldPosition().setWidth(width + 15);
        }

        canvas.setPlay(clone);

        if(loadNew) {
            deselectPositionsAndActions();
        }
        if(!currentPlay.validate()) {
            field.addStyleName("playnotvalid");
        }
    }

    /**
     * Goes to initial state: no image selected and options are hidden.<br>
     * Used when showing a new play or right-click at the field.
     */
    private void deselectPositionsAndActions() {
        deselectImage(imgSelectedPosition);
        imgSelectedPosition = null;
        addNewAction = false;
        optionActionTypes.setVisible(false);
        chkPrimaryAction.setVisible(false);
        chkPrimaryAction.setEnabled(true);
    }

    /**
     * Sets the source of the given image to default background.
     *
     * @param img The image to set the source at.
     */
    private void deselectImage(Image img) {
        if(img != null) {
            img.setSource(resImgBackground);
        }
    }

    /**
     * Sets the visibility of the play (routes). The formation will be visible after that call in any case.
     */
    public void setPlayVisible(boolean visible) {
        canvas.setVisible(visible);
    }

    /**
     * Gets whether the play (routes) is visible or not.
     */
    public boolean isPlayVisible() {
        return canvas.isVisible();
    }

    @Override
    protected int convertLayoutPositionToHeight(float layoutHeight) {
        return -Math.round((layoutHeight - offsetHeight) / 10f);
    }

    /**
     * Sets the content of the ActionType-Selector depending on the position of the currently selected image/position.<br>
     * Does nothing if no image is selected.
     *
     * @see ActionType#getActionTypes(Position, PlayType)
     */
    private void showAvailableActions() {
        if(imgSelectedPosition != null) {
            optionActionTypes.removeAllItems();

            // show only available actions for the selected position
            PositionWrapper pw = (PositionWrapper)imgSelectedPosition.getData();
            optionActionTypes.addItems(ActionType.getActionTypes(pw.getPosition(), optionPlayType.getValue()));
        }
    }

    /**
     * Creates a new {@link Action} at the <tt>currentPlay</tt> with the given parameters and refreshes the play in
     * canvas.
     * <p>
     * If the given direction is equal to current {@link FieldPosition} of the given <tt>PositionWrapper</tt> this
     * method does not add an action.
     *
     * @param pw The PositionWrapper the Action is created for.
     * @param actionType
     * @param direction
     * @param primaryReceive
     */
    private void addAction(PositionWrapper pw, ActionType actionType, FieldPosition direction, boolean primaryReceive) {
        if(!pw.getFieldPosition().equals(direction)) {
            currentPlay.addAction(pw, new Action(actionType, direction, primaryReceive));
            showPlay(currentPlay, false);
        }
    }

    /**
     * Called when selecting an {@link ActionType}. Removes all <tt>Actions</tt> at the <tt>currentPlay</tt> for the
     * selected <tt>PositionWrapper</tt>.<br>
     * If the selected ActionType is either <tt>ActionType.RUN_BLOCK</tt> or <tt>ActionType.PASS_BLOCK</tt> or
     * <tt>ActionType.BLITZ</tt> or <tt>ActionType.MAN</tt>, a simple default action will be created without having to
     * click on canvas. This is marked in {@link PlayFormationField#addedAutoAction} to remove it when clicking on
     * canvas.<br>
     * The special thing with <tt>ActionType.MAN</tt> is, that the default route is equal to the added route when
     * clicking on the field, no matter where the click happens. This is because man-to-man coverage doesn't have a
     * route/direction; the action is just the type. You cannot see neither the default route nor the added route
     * after a mouse click.
     * <p>
     * Finally the play is refreshed after changing the <tt>ActionType</tt>.
     */
    private class ActionTypeChangeListener implements ButtonOption.ValueChangeListener<ActionType> {
        @Override
        public void valueChange(ActionType value) {
            addNewAction = true;

            PositionWrapper pw = (PositionWrapper)imgSelectedPosition.getData();
            currentPlay.removeAction(pw);

            if(value.isReceiveType() && currentPlay.isPassPlay()) {
                chkPrimaryAction.setVisible(true);
                chkPrimaryAction.setValue(false);
            } else {
                chkPrimaryAction.setVisible(false);
            }

            // create simple default block route when selecting run or pass block action
            if(value.equals(ActionType.RUN_BLOCK) || value.equals(ActionType.PASS_BLOCK)
                    || value.equals(ActionType.BLITZ) || value.equals(ActionType.MAN)) {
                addedAutoAction = true;
                int heightDir = value.equals(ActionType.RUN_BLOCK) ? 1 : -1;
                heightDir *= value.equals(ActionType.BLITZ) ? 3 : 1; // does nothing, looks better :)
                addAction(pw, value, pw.getFieldPosition().add(heightDir, 0), false);
            } else {
                // addAction() will call that method
                showPlay(currentPlay, false);
            }
        }
    }

    /**
     * Sets the PlayType at the <tt>currentPlay</tt>, refreshes available ActionTypes and validates the play. This is
     * useful when the PlayType changed from pass to play or reverse.
     */
    private class PlayTypeChangeListener implements ButtonOption.ValueChangeListener<PlayType> {
        @Override
        public void valueChange(PlayType value) {
            currentPlay.setPlayType(value);
            showAvailableActions();

            field.removeStyleName("playnotvalid");
            if(!currentPlay.validate()) {
                field.addStyleName("playnotvalid");
            }
        }
    }

    /**
     * This LayoutClickListener gets the corresponding image to the clicked position in the AbsoluteLayout.<br>
     * This is needed because we cannot add a click listener directly to the images because the Canvas lays at the top
     * of all images, which causes not to listen to image click events.
     * <p>
     * It also creates Actions on a click event when not selecting images.
     */
    private class FieldClickListener implements LayoutClickListener {

        @Override
        public void layoutClick(LayoutClickEvent event) {
            if(!isEnabled()) {
                return; // not needed
            }

            if(MouseButton.LEFT.equals(event.getButton())) {

                if(!addNewAction) {
                    Image clickedImage = findClickedImage(event.getRelativeX(), event.getRelativeY());

                    if(clickedImage != null) { // clicked an image
                        deselectImage(imgSelectedPosition); // deselect old image
                        imgSelectedPosition = clickedImage;
                        imgSelectedPosition.setSource(resImgBackgroundSelected); // select new image

                        showAvailableActions();
                        optionActionTypes.setVisible(true);
                    }
                } else if(optionActionTypes.getValue() != null) { // actionType must be selected
                    addAction(event.getRelativeX(), event.getRelativeY());
                }
            } else if(MouseButton.RIGHT.equals(event.getButton())) {
                deselectPositionsAndActions();
            }
        }

        /**
         * Adds an {@link Action} with the selected <tt>ActionType</tt> and a direction of the given (layout)
         * coordinates.<br>
         * The coordinates are being converted to logical coordinates in this method.
         */
        private void addAction(float mouseX, float mouseY) {
            PositionWrapper pw = (PositionWrapper)imgSelectedPosition.getData();

            if(addedAutoAction) { // remove automatically created route
                currentPlay.removeAction(pw);
            }
            addedAutoAction = false;

            // create FieldPosition from mouse coordinates
            int height = convertLayoutPositionToHeight(mouseY - 15); // 15 = half size of image
            int width = convertLayoutPositionToWidth(mouseX - 15);
            FieldPosition direction = new FieldPosition(height, width);

            // create a new action
            boolean primaryReceive = chkPrimaryAction.isVisible() ? chkPrimaryAction.getValue() : false;
            PlayFormationField.this.addAction(pw, optionActionTypes.getValue(), direction, primaryReceive);
            chkPrimaryAction.setEnabled(false);
        }

        /**
         * Returns the corresponding image to the clicked position. Returns <tt>null</tt> if the clicked position does
         * not match an image position.
         */
        private Image findClickedImage(float mouseX, float mouseY) {
            // iterate over all components in field (AbsoluteLayout)
            for(Iterator<Component> iter = field.iterator(); iter.hasNext();) {
                Component component = iter.next();

                // check only images and only those with data (see createPositionImage())
                if(component instanceof Image && ((Image)component).getData() != null) {
                    ComponentPosition imgPos = field.getPosition(component);

                    if(mouseX > imgPos.getLeftValue()
                            && mouseX < imgPos.getLeftValue() + 30 // 30 = image size
                            && mouseY > imgPos.getTopValue()
                            && mouseY < imgPos.getTopValue() + 30) {
                        return (Image)component;
                    }
                }
            }

            return null;
        }
    }
}
