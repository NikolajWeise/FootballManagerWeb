package de.weise.fm.web.components;

import com.vaadin.data.Property;
import com.vaadin.ui.TextField;

/**
 * Read-only {@link TextField}, which allows setting the value with temporarily disabling read-only flag.
 * <p>
 * Note that this will cause security issues, so do not use this class if the user input is forbidden in every case.
 * This class is used to show the read-only mode to the user, but allow programmatically changes to the value.
 * <p>
 * Use in scss-file:
 * <pre>
 * .v-slot-editablereadonly {
 *     .v-textfield-readonly {
 *         <i>attributes...</i>
 *     }
 * }
 * </pre>
 *
 * @see TextField
 * @see TextField#setReadOnly(boolean)
 */
public class EditableReadonlyTextField extends TextField {

    public EditableReadonlyTextField() {
        super.addStyleName("editablereadonly");
        super.setReadOnly(true);
        super.setWidth("200px");
    }

    @Override
    public synchronized void setValue(String newValue)
            throws Property.ReadOnlyException {
        super.setReadOnly(false);
        super.setValue(newValue);
        super.setReadOnly(true);
    }
}
