/*
 * ------------------------------------------------------------------------
 *
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
 *   17.10.2016 (Jonathan Hale): created
 */
package org.knime.base.node.jsnippet.util.field;

import org.knime.core.data.DataType;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.config.Config;

/**
 * Fields representing knime data columns.
 *
 * @author Heiko Hofer
 * @author Jonathan Hale, KNIME, Konstanz, Germany
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 * @noreference This class is not intended to be referenced by clients.
 */
public abstract class JavaColumnField extends JavaField {
    /**
     * DataType used for display and finding replacements in case the loaded converter factory id was invalid.
     */
    protected DataType m_knimeType;

    /**
     * Id of the converter factory to use for this field.
     */
    protected String m_converterFactoryId = null;

    /**
     * @return the knimeType
     */
    public DataType getDataType() {
        return m_knimeType;
    }

    @Override
    public void saveSettings(final Config config) {
        super.saveSettings(config);
        config.addDataType(KNIME_TYPE, m_knimeType);

        if (m_converterFactoryId != null) {
            config.addString(CONV_FACTORY, m_converterFactoryId);
        }
    }

    @Override
    public void loadSettings(final Config config) throws InvalidSettingsException {
        super.loadSettings(config);
        m_knimeType = config.getDataType(KNIME_TYPE);
        m_converterFactoryId = config.getString(CONV_FACTORY, null);
    }

    @Override
    public FieldType getFieldType() {
        return FieldType.Column;
    }

    /**
     * Id of the converter factory used to convert from column to java field type or vice versa. Depending on whether
     * this is an in or out field this refers to:
     * <ul>
     * <li>InCol: {@link org.knime.core.data.convert.java.DataCellToJavaConverterFactory}</li>
     * <li>OutCol: {@link org.knime.core.data.convert.datacell.JavaToDataCellConverterFactory}</li>
     * </ul>
     *
     * @return the converter factory id
     */
    public String getConverterFactoryId() {
        return m_converterFactoryId;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof JavaColumnField)) {
            return false;
        }

        if (((JavaColumnField)other).getConverterFactoryId() != getConverterFactoryId()) {
            return false;
        }

        return super.equals(other);
    }
}