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
 *   Oct 10, 2016 (simon): created
 */
package org.knime.time.util;

import org.knime.core.data.DataType;
import org.knime.core.data.time.localdate.LocalDateCellFactory;
import org.knime.core.data.time.localdatetime.LocalDateTimeCellFactory;
import org.knime.core.data.time.localtime.LocalTimeCellFactory;
import org.knime.core.data.time.zoneddatetime.ZonedDateTimeCellFactory;

/**
 * An enumeration that contains all different Date&Time types and holds its {@link DataType} and a representing name.
 *
 * @author Simon Schmid, KNIME.com, Konstanz, Germany
 */
public enum DateTimeType {
        /**
         * Contains only a date.
         */
        LOCAL_DATE("Date", LocalDateCellFactory.TYPE),
        /**
         * Contains only a time.
         */
        LOCAL_TIME("Time", LocalTimeCellFactory.TYPE),
        /**
         * Contains a date and a time.
         */
        LOCAL_DATE_TIME("Date&time", LocalDateTimeCellFactory.TYPE),
        /**
         * Contains a date, a time and a time zone.
         */
        ZONED_DATE_TIME("Zoned date&time", ZonedDateTimeCellFactory.TYPE);

    private final String m_name;

    private final DataType m_dataType;

    private DateTimeType(final String name, final DataType dataType) {
        m_name = name;
        m_dataType = dataType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return m_name;
    }

    /**
     * Returns the {@link DataType} of this enum constant.
     *
     * @return the representing {@link DataType}
     */
    public DataType getDataType() {
        return m_dataType;
    }
}
