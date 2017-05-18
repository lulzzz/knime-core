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
 *   21.08.2015 (thor): created
 */
package org.knime.core.data.time.zoneddatetime;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataCellFactory.FromComplexString;
import org.knime.core.data.DataCellFactory.FromSimpleString;
import org.knime.core.data.DataType;
import org.knime.core.data.convert.DataCellFactoryMethod;
import org.knime.core.node.util.CheckUtils;

/**
 * Factory for creating {@link ZonedDateTimeCell}.
 *
 * @author Simon Schmid, KNIME.com, Konstanz, Germany
 * @since 3.3
 */
public final class ZonedDateTimeCellFactory implements FromSimpleString, FromComplexString {
    /**
     * The data type for the cells created by this factory.
     */
    public static final DataType TYPE = ZonedDateTimeCell.TYPE;

    @Override
    public DataType getDataType() {
        return TYPE;
    }

    @DataCellFactoryMethod(name = "String (yyyy-MM-dd'T'HH:mm:ssXXX'['zzzz']')")
    @Override
    public DataCell createCell(final String s) {
        return create(s);
    }

    /**
     * Creates a new ZonedDateTimeCell from a string such as "2007-12-03T10:15:30+01:00[Europe/Paris]". For details see
     * {@link ZonedDateTime#parse(CharSequence)}.
     *
     * @param s the string to parse into {@link ZonedDateTimeCell}, not null.
     * @return the cell containing the parsed ZonedDateTime.
     * @throws IllegalArgumentException when the string is null.
     * @throws DateTimeParseException as per {@link ZonedDateTime#parse(CharSequence)}
     */
    public static DataCell create(final String s) {
        return create(ZonedDateTime.parse(CheckUtils.checkArgumentNotNull(s, "Argument must not be null")));
    }

    /**
     * Creates a new ZonedDateTimeCell from the arguments, see
     * {@link ZonedDateTime#parse(CharSequence, DateTimeFormatter)}.
     *
     * @param s Non-null string to parse.
     * @param formatter Non-null formatter to use.
     * @return the cell containing the parsed ZonedDateTime.
     * @throws IllegalArgumentException when either argument is null.
     * @throws DateTimeParseException as per {@link ZonedDateTime#parse(CharSequence, DateTimeFormatter)}
     * @see ZonedDateTime#parse(CharSequence, DateTimeFormatter)
     */
    public static DataCell create(final String s, final DateTimeFormatter formatter) {
        ZonedDateTime zonedDateTime =
            ZonedDateTime.parse(CheckUtils.checkArgumentNotNull(s, "String argument must not be null"),
                CheckUtils.checkArgumentNotNull(formatter, "Formatter argument must not be null"));
        return create(zonedDateTime);
    }

    /**
     * Creates a new ZonedDateTimeCell from the argument.
     *
     * @param zonedDateTime Non-null argument to wrap.
     * @return the cell containing the parsed ZonedDateTime.
     * @throws IllegalArgumentException when the argument is null.
     */
    @DataCellFactoryMethod(name = "ZonedDateTime")
    public static DataCell create(final ZonedDateTime zonedDateTime) {
        return new ZonedDateTimeCell(CheckUtils.checkArgumentNotNull(zonedDateTime, "Argument must not be null"));
    }

}
