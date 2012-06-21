/*
 * This library is part of OpenCms -
 * the Open Source Content Management System
 *
 * Copyright (c) Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.opencms.ade.contenteditor.client.widgets;

import com.alkacon.acacia.client.widgets.I_EditWidget;

import org.opencms.gwt.client.ui.input.CmsCheckBox;
import org.opencms.gwt.client.ui.input.CmsMultiCheckBox;
import org.opencms.gwt.client.ui.input.CmsPaddedPanel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;

/**
 * Provides a standard HTML form checkbox widget, for use on a widget dialog.<p>
 * 
 * 
 * @since 8.5.0
 * 
 * */
public class CmsMultiCheckboxWidget extends Composite implements I_EditWidget {

    private boolean m_active = true;
    private CmsMultiCheckBox m_multicheckbox;
    private String m_selected;

    /**
     * Constructs an OptionalTextBox with the given caption on the check.<p>
     * 
     * @param config configuration string
     * 
     */
    public CmsMultiCheckboxWidget(String config) {

        m_multicheckbox = new CmsMultiCheckBox(parseconfig(config));
        setValue(m_selected);
        // Place the check above the text box using a vertical panel.
        CmsPaddedPanel panel = new CmsPaddedPanel(10);
        // All composites must call initWidget() in their constructors.
        initWidget(panel);
        panel.add(m_multicheckbox);

        List<CmsCheckBox> checkboxes = m_multicheckbox.getCheckboxes();
        Iterator<CmsCheckBox> it = checkboxes.iterator();
        while (it.hasNext()) {
            it.next().addValueChangeHandler(new ValueChangeHandler<Boolean>() {

                public void onValueChange(ValueChangeEvent<Boolean> arg0) {

                    fireChangeEvent();

                }

            });
        }

    }

    /**
     * @see com.google.gwt.event.dom.client.HasFocusHandlers#addFocusHandler(com.google.gwt.event.dom.client.FocusHandler)
     */
    public HandlerRegistration addFocusHandler(FocusHandler handler) {

        return null;
    }

    /**
     * @see com.google.gwt.event.logical.shared.HasValueChangeHandlers#addValueChangeHandler(com.google.gwt.event.logical.shared.ValueChangeHandler)
     */
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {

        return addHandler(handler, ValueChangeEvent.getType());
    }

    /**
     *  Represents a value change event.<p>
     */
    public void fireChangeEvent() {

        ValueChangeEvent.fire(this, m_multicheckbox.getFormValueAsString());
    }

    /**
    

    /**
     * @see com.google.gwt.user.client.ui.HasValue#getValue()
     */
    public String getValue() {

        return m_multicheckbox.getFormValueAsString();
    }

    /**
     * @see com.alkacon.acacia.client.widgets.I_EditWidget#isActive()
     */
    public boolean isActive() {

        return m_active;
    }

    /**
     * @see com.alkacon.acacia.client.widgets.I_EditWidget#onAttachWidget()
     */
    public void onAttachWidget() {

        super.onAttach();
    }

    /**
     * @see com.alkacon.acacia.client.widgets.I_EditWidget#setActive(boolean)
     */
    public void setActive(boolean active) {

        m_active = active;
        if (active) {
            fireChangeEvent();
        }
        m_multicheckbox.setEnabled(active);

    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object)
     */
    public void setValue(String value) {

        setValue(value, true);

    }

    /**
     * @see com.google.gwt.user.client.ui.HasValue#setValue(java.lang.Object, boolean)
     */
    public void setValue(String value, boolean fireEvents) {

        m_selected = value;
        m_multicheckbox.setFormValueAsString(value);
        if (fireEvents) {
            fireChangeEvent();
        }

    }

    /**
     * Helper class for parsing the configuration in to a list of Radiobuttons. <p>
     * 
     * @param config the configuration string.
     * */
    private Map<String, String> parseconfig(String config) {

        Map<String, String> result = new HashMap<String, String>();
        m_selected = "";
        String[] labels = config.split("\\|");
        for (int i = 0; i < labels.length; i++) {

            if (labels[i].indexOf("*") >= 0) {
                labels[i] = labels[i].replace("*", "");
                m_selected += labels[i] + "|";
            }
            result.put(labels[i], labels[i]);
        }
        m_selected = m_selected.substring(0, m_selected.lastIndexOf("|"));
        return result;
    }
}