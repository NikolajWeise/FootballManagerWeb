package de.weise.fm.web.components.layout;

import java.util.Collection;

import com.vaadin.event.MouseEvents;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbsoluteLayout.ComponentPosition;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.DragAndDropWrapper.WrapperTargetDetails;
import com.vaadin.ui.DragAndDropWrapper.WrapperTransferable;
import com.vaadin.ui.Image;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import de.weise.fm.web.components.js.buttonoption.ButtonOption;
import de.weise.fm.web.model.play.FieldPosition;
import de.weise.fm.web.model.play.Formation;
import de.weise.fm.web.model.play.Position;
import de.weise.fm.web.model.play.PositionWrapper;

public class FormationField extends CustomComponent {

    protected final int IMG_SIZE = 25;
    protected int height;
    protected int width;
    protected int offsetHeight = 0;

    protected AbsoluteLayout field;
    private Image imgSelectedPosition;
    private ButtonOption<Position> optionPositions;

    public FormationField(int height, int width) {
        this.height = height;
        this.width = width;

        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);
        layout.addStyleName("no-vertical-drag-hints");
        layout.addStyleName("no-horizontal-drag-hints");
        layout.addStyleName("no-box-drag-hints");

        // TODO show a nice background on the field (yard lines, LoS, sidelines, ...)
        field = new AbsoluteLayout();
        field.addStyleName("formation");
        field.setHeight(height, Unit.PIXELS);
        field.setWidth(width, Unit.PIXELS);

        initComponents(layout);
    }

    /**
     * Initializes additional components to the field (AbsoluteLayout).
     *
     * @param layout The CompositionRoot of this CustomComponent.
     */
    protected void initComponents(Layout layout) {
        DragAndDropWrapper fieldWrapper = new DragAndDropWrapper(field);
        fieldWrapper.setSizeUndefined();
        fieldWrapper.setDropHandler(new PositionDropHandler());
        layout.addComponent(fieldWrapper);

        optionPositions = new ButtonOption<>();
        optionPositions.addStyleName("btn-primary");
        optionPositions.setVisible(false);
        optionPositions.addValueChangeListener(new PositionChangeListener());
        layout.addComponent(optionPositions);
    }

    /**
     * Adds the specified positions to the component which shows selectable positions.
     */
    public void setSelectablePositions(Collection<Position> positions) {
        for(Position p : positions) {
            optionPositions.addItem(p);
        }
    }

    /**
     * Shows the given formation in the field.
     * <p>
     * Note that this method will remove all components from the field (AbsoluteLayout).
     */
    public void showFormation(Formation formation) {
        field.removeAllComponents();

        for(PositionWrapper pw : formation.getPositions()) {

            int height = Math.abs(pw.getFieldPosition().getHeight());
            int width = pw.getFieldPosition().getWidth();
            String cssPosition = getCssPosition(height, width);

            createPositionImage(pw, cssPosition);
        }
    }

    /**
     * Creates an image and adds it to the field at the given CSS-position.
     *
     * @param pw The PositionWrapper from the formation.
     * @param cssPosition The CSS-position String which contains coordinates where to set the image in the
     *        AbsoluteLayout.
     */
    protected void createPositionImage(PositionWrapper pw, String cssPosition) {
        if(Position.C.equals(pw.getPosition())
                || Position.RT.equals(pw.getPosition()) || Position.LT.equals(pw.getPosition())
                || Position.RG.equals(pw.getPosition()) || Position.LG.equals(pw.getPosition())) {
            createNonEditableImage(pw, cssPosition);
        } else {
            createEditaleImage(pw, cssPosition);
        }
    }

    /**
     * Creates a position image and another background image. Those images don't get any user input handlers and cannot
     * be clicked or moved.
     */
    private void createNonEditableImage(PositionWrapper pw, String cssPosition) {
        Image img = new Image(null, new ThemeResource("img/positions/" + pw.getPosition().name() + ".png"));
        img.addStyleName("notmovable");

        Image imgCircle = new Image(null, new ThemeResource("img/positions/circle_red.png"));

        field.addComponent(imgCircle, cssPosition);
        field.addComponent(img, cssPosition);
    }

    /**
     * Creates a position image with a drag&drop-wrapper and a ClickListener.
     */
    private void createEditaleImage(PositionWrapper pw, String cssPosition) {
        Image img = new Image(null, new ThemeResource("img/positions/" + pw.getPosition().name() + ".png"));
        img.setData(pw);
        img.addClickListener(new PositionClickListener());

        DragAndDropWrapper imgWrapper = new DragAndDropWrapper(img);
        imgWrapper.setDragStartMode(DragStartMode.COMPONENT);
        imgWrapper.setData(pw);

        field.addComponent(imgWrapper, cssPosition);
    }

    /**
     * Converts the given <tt>height</tt> and <tt>width</tt> to layout coordinates and returns it as CSS-String.
     *
     * @param height The (logical) height from the FieldPosition.
     * @param width The (logical) width from the FieldPosition.
     * @return
     */
    protected String getCssPosition(int height, int width) {
        height = convertHeightToLayoutPosition(height);
        width = convertWidthToLayoutPosition(width);
        return "top:" + height + "px;left:" + width + "px";
    }

    /**
     * Sets the height offset position where to start drawing images.
     * <p>
     * For example if offset is set to 0, an image to a player with a logical height of 0 will be drawn at layout
     * position 0. If offset is set to 100, an image to a player with a logical height of 0 will be drawn at layout
     * position 100.
     */
    public void setHeightOffset(int offsetHeight) {
        this.offsetHeight = offsetHeight;
    }

    protected int convertHeightToLayoutPosition(int logicalHeight) {
        return offsetHeight + logicalHeight * 10;
    }

    protected int convertLayoutPositionToHeight(float layoutHeight) {
        int h = -Math.round((layoutHeight - offsetHeight) / 10f);
        return Math.min(0, h);
    }

    protected int convertWidthToLayoutPosition(int logicalWidth) {
        // this.width / 2 => center to layout
        // IMG_SIZE / 2 => center image
        int layoutPos = this.width / 2 + logicalWidth * 10 - IMG_SIZE / 2;
        return layoutPos;
    }

    protected int convertLayoutPositionToWidth(float layoutWidth) {
        int w = Math.round(layoutWidth + IMG_SIZE / 2);
        w = (w - this.width / 2) / 10;

        w = Math.max(-20, w);
        return Math.min(20, w);
    }

    /**
     * Reacts on dropping a position image to the field. Calculates logical coordinates from the layout coordinates and
     * sets them to the PositionWrapper of the image.<br>
     * In other words, moves a position in a formation when dragging&dropping it.
     */
    private class PositionDropHandler implements DropHandler {

        @Override
        public AcceptCriterion getAcceptCriterion() {
            // TODO do NOT accept all components, but only images from the field!
            // when adding further draggable components to the same page, it will be funny to drop them here :)
            return AcceptAll.get();
        }

        @Override
        public void drop(DragAndDropEvent event) {
            WrapperTransferable t = (WrapperTransferable)event.getTransferable();
            WrapperTargetDetails details = (WrapperTargetDetails)event.getTargetDetails();
            if(t.getDraggedComponent() == null) {
                return;
            }

            // calculate the drag coordinate difference
            int xChange = details.getMouseEvent().getClientX() - t.getMouseDownEvent().getClientX();
            int yChange = details.getMouseEvent().getClientY() - t.getMouseDownEvent().getClientY();

            // get the component position in the absolute layout
            ComponentPosition pos = field.getPosition(t.getSourceComponent());
            float topValue = pos.getTopValue() + yChange;
            float leftValue = pos.getLeftValue() + xChange;

            // calculate new logical coordinates
            int newLogicalHeight = convertLayoutPositionToHeight(topValue);
            int newLogicalWidth = convertLayoutPositionToWidth(leftValue);

            // move the dragged component to new position
            pos.setCSSString(getCssPosition(Math.abs(newLogicalHeight), newLogicalWidth));

            // set fieldPosition
            PositionWrapper pw = (PositionWrapper)((DragAndDropWrapper)t.getSourceComponent()).getData();
            pw.setFieldPosition(new FieldPosition(newLogicalHeight, newLogicalWidth));
        }
    }

    /**
     * Called when clicking a position image. Adds a "selected"-style to the clicked image and removes it from the image
     * clicked before. Also sets the value of selectable positions to the position of the clicked image.
     */
    private class PositionClickListener implements MouseEvents.ClickListener {
        @Override
        public void click(MouseEvents.ClickEvent event) {
            if(imgSelectedPosition != null) {
                imgSelectedPosition.getParent().removeStyleName("selected"); // old selection
            }
            imgSelectedPosition = (Image)event.getComponent();
            imgSelectedPosition.getParent().addStyleName("selected"); // new selection

            PositionWrapper selectedPosition = (PositionWrapper)imgSelectedPosition.getData();
            optionPositions.setValue(selectedPosition.getPosition()); // select current position in comboBox
            optionPositions.setVisible(!Position.QB.equals(selectedPosition.getPosition())); // show comboBox if not QB
        }
    }

    /**
     * Changes the {@link Position} of the selected position (image) in the formation.
     */
    private class PositionChangeListener implements ButtonOption.ValueChangeListener<Position> {
        @Override
        public void valueChange(Position value) {
            PositionWrapper selectedPosition = (PositionWrapper)imgSelectedPosition.getData();
            selectedPosition.setPosition(value);
            imgSelectedPosition.setSource(new ThemeResource("img/positions/" + value.name() + ".png"));
        }
    }
}
