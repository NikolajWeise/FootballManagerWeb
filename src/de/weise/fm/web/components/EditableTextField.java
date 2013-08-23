package de.weise.fm.web.components;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

/**
 * The <tt>EditableTextField</tt> consists of a {@link EditableReadonlyTextField} and a {@link Button} to set the
 * readOnly mode, inside a HorizontalLayout.
 */
public class EditableTextField extends CustomComponent {

    private TextField txt;
    private Button btn;

    public EditableTextField(String btnText) {
        txt = new EditableReadonlyTextField();
        btn = new Button(btnText, new ButtonClick());

        setCompositionRoot(new HorizontalLayout(txt, btn));
    }

    public String getValue() {
        return txt.getValue();
    }

    public void setValue(String value) {
        txt.setValue(value);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        txt.setReadOnly(readOnly);
    }

    @Override
    public void setEnabled(boolean enabled) {
        btn.setEnabled(enabled);
    }

    private class ButtonClick
            implements ClickListener {

        @Override
        public void buttonClick(ClickEvent event) {
            txt.setReadOnly(false);
        }
    }
}
