/*
 * ------------------------------------------------------------------------
 *  Copyright by KNIME GmbH, Konstanz, Germany
 *  Website: http://www.knime.org; Email: contact@knime.org
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 *  Additional permission under GNU GPL version 3 section 7:
 *
 *  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
 *  Hence, KNIME and ECLIPSE are both independent programs and are not
 *  derived from each other. Should, however, the interpretation of the
 *  GNU GPL Version 3 ("License") under any applicable laws result in
 *  KNIME and ECLIPSE being a combined program, KNIME GMBH herewith grants
 *  you the additional permission to use and propagate KNIME together with
 *  ECLIPSE with only the license terms in place for ECLIPSE applying to
 *  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
 *  license terms of ECLIPSE themselves allow for the respective use and
 *  propagation of ECLIPSE together with KNIME.
 *
 *  Additional permission relating to nodes for KNIME that extend the Node
 *  Extension (and in particular that are based on subclasses of NodeModel,
 *  NodeDialog, and NodeView) and that only interoperate with KNIME through
 *  standard APIs ("Nodes"):
 *  Nodes are deemed to be separate and independent programs and to not be
 *  covered works.  Notwithstanding anything to the contrary in the
 *  License, the License does not apply to Nodes, you are not required to
 *  license Nodes under the License, and you are granted a license to
 *  prepare and propagate Nodes, in each case even if such Nodes are
 *  propagated with or for interoperation with KNIME.  The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * ------------------------------------------------------------------------
 *
 * History
 *   29.09.2009 (Fabian Dill): created
 */
package org.knime.timeseries.node.mask;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.knime.core.data.date.DateAndTimeValue;
import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentBoolean;
import org.knime.core.node.defaultnodesettings.DialogComponentButtonGroup;
import org.knime.core.node.defaultnodesettings.DialogComponentColumnNameSelection;
import org.knime.core.node.defaultnodesettings.DialogComponentString;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelString;

/**
 * Dialog with a column selection and radio buttons to either mask date or
 * time or milliseconds only.
 *
 * @author Fabian Dill, KNIME.com, Zurich, Switzerland
 */
@Deprecated
public class MaskTimeNodeDialog extends DefaultNodeSettingsPane {

    private SettingsModelString m_newColName = MaskTimeNodeModel.createNewColumnNameModel();

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public MaskTimeNodeDialog() {
        addDialogComponent(new DialogComponentColumnNameSelection(
                MaskTimeNodeModel.createColumnSelectionModel(),
                "Select the column that should be masked", 0,
                DateAndTimeValue.class));

        final SettingsModelBoolean replaceCol = MaskTimeNodeModel.createReplaceColumnModel();
        replaceCol.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(final ChangeEvent e) {
                m_newColName.setEnabled(!replaceCol.getBooleanValue());
            }
        });

        addDialogComponent(new DialogComponentBoolean(replaceCol, "Replace column"));

        addDialogComponent(new DialogComponentString(m_newColName, "Appended column name"));
        m_newColName.setEnabled(!replaceCol.getBooleanValue());

        addDialogComponent(new DialogComponentButtonGroup(
                MaskTimeNodeModel.createMaskSelectionModel(), true, "Mask:",
                MaskTimeNodeModel.MASK_DATE,
                MaskTimeNodeModel.MASK_TIME,
                MaskTimeNodeModel.MASK_MILLIS
        ));
    }

}
