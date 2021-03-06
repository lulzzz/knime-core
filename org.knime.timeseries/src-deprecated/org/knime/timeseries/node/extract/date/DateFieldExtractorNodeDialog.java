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
 *   05.10.2009 (Fabian Dill): created
 */
package org.knime.timeseries.node.extract.date;

import org.knime.core.data.date.DateAndTimeValue;
import org.knime.core.node.defaultnodesettings.DialogComponentColumnNameSelection;
import org.knime.timeseries.node.extract.AbstractFieldExtractorNodeDialog;

/**
 * Node dialog for the date field extractor node that configures which of the
 * date fields (year, quarter, month, week, day, day of week) should be
 * appended as an int column.
 *
 * @author Fabian Dill, KNIME.com, Zurich, Switzerland
 */
@Deprecated
public class DateFieldExtractorNodeDialog
    extends AbstractFieldExtractorNodeDialog {

    /** Year name. */
    static final String YEAR = "Year";
    /** Quarter name.*/
    static final String QUARTER = "Quarter";
    /** Month name. */
    static final String MONTH = "Month";
    /** Day of month name. */
    static final String DAY_OF_MONTH = "Day of month";
    /** Day of week name. */
    static final String DAY_OF_WEEK = "Day of week";
    /** Day of year name. */
    static final String DAY_OF_YEAR = "Day of year";
    /** Week of year name. 
     * @since 2.9 */
    static final String WEEK_OF_YEAR = "Week of year";


    /**
     *
     */
    @SuppressWarnings("unchecked")
    public DateFieldExtractorNodeDialog() {
        addDialogComponent(new DialogComponentColumnNameSelection(
                createColumnSelectionModel(),
                "Column to extract time fields from:", 0,
                DateAndTimeValue.class));
        createUIComponentFor(YEAR);
        createUIComponentFor(QUARTER);
        // the month UI component looks differently because of
        // the string or int radio buttons
        createUIComponentWithFormatSelection(MONTH);
        createUIComponentFor(DAY_OF_MONTH);
        createUIComponentWithFormatSelection(DAY_OF_WEEK);
        createUIComponentFor(DAY_OF_YEAR);
        // new since 2.9.1
        createUIComponentFor(WEEK_OF_YEAR);
    }

}
