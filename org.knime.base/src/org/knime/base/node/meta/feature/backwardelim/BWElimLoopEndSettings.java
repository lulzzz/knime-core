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
 * ---------------------------------------------------------------------
 *
 * History
 *   26.02.2008 (thor): created
 */
package org.knime.base.node.meta.feature.backwardelim;

import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;

/**
 * This class contains th settings for the elimination loop's tail node.
 *
 * @author Thorsten Meinl, University of Konstanz
 */
public class BWElimLoopEndSettings {
    private String m_predictionColumn;
    private String m_targetColumn;

    /**
     * Returns the target column's name.
     *
     * @return the column's name
     */
    public String targetColumn() {
        return m_targetColumn;
    }

    /**
     * Sets the target column's name.
     *
     * @param targetColumn the column's name
     */
    public void targetColumn(final String targetColumn) {
        m_targetColumn = targetColumn;
    }



    /**
     * Returns the prediction column's name.
     *
     * @return the column's name
     */
    public String predictionColumn() {
        return m_predictionColumn;
    }

    /**
     * Sets the prediction column's name.
     *
     * @param targetColumn the column's name
     */
    public void predictionColumn(final String targetColumn) {
        m_predictionColumn = targetColumn;
    }

    /**
     * Saves the settings from this object into the passed node settings object.
     *
     * @param settings a node settings object
     */
    public void saveSettings(final NodeSettingsWO settings) {
        settings.addString("predictionColumn", m_predictionColumn);
        settings.addString("targetColumn", m_targetColumn);
    }

    /**
     * Loads the settings from passed node settings object into this object.
     *
     * @param settings a node settings object
     * @throws InvalidSettingsException if a settings is missing
     */
    public void loadSettings(final NodeSettingsRO settings)
            throws InvalidSettingsException {
        m_predictionColumn = settings.getString("predictionColumn");
        m_targetColumn = settings.getString("targetColumn");
    }

    /**
     * Loads the settings from passed node settings object into this object
     * using default values if a settings is missing.
     *
     * @param settings a node settings object
     */
    public void loadSettingsForDialog(final NodeSettingsRO settings) {
        m_predictionColumn = settings.getString("predictionColumn", null);
        m_targetColumn = settings.getString("targetColumn", null);
    }
}
