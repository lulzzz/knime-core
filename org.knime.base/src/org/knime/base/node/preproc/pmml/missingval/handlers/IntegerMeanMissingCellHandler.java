/*
 * ------------------------------------------------------------------------
 *
 *  Copyright by
 *  University of Konstanz, Germany and
 *  KNIME GmbH, Konstanz, Germany
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
 *   13.01.2015 (Alexander): created
 */
package org.knime.base.node.preproc.pmml.missingval.handlers;

import org.dmg.pmml.DATATYPE;
import org.dmg.pmml.DerivedFieldDocument.DerivedField;
import org.knime.base.data.statistics.CellMean;
import org.knime.base.data.statistics.Statistic;
import org.knime.base.node.preproc.pmml.missingval.DataColumnWindow;
import org.knime.base.node.preproc.pmml.missingval.DefaultMissingCellHandler;
import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DoubleValue;
import org.knime.core.data.RowKey;
import org.knime.core.data.def.IntCell;
import org.knime.core.data.def.LongCell;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;

/**
 * A handler that replaces missing values with the rounded mean of the column.
 * @author Alexander Fillbrunn
 */
public class IntegerMeanMissingCellHandler extends DefaultMissingCellHandler {

    private CellMean m_mean;

    /**
     * @param col the column this handler is configured for
     */
    public IntegerMeanMissingCellHandler(final DataColumnSpec col) {
        super(col);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadSettingsFrom(final NodeSettingsRO settings) throws InvalidSettingsException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveSettingsTo(final NodeSettingsWO settings) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Statistic getStatistic() {
        if (m_mean == null) {
            m_mean = new CellMean(getColumnSpec().getName());
        }
        return m_mean;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataCell getCell(final RowKey key, final DataColumnWindow window) {
        DataCell result = m_mean.getResult(0);
        // NaN is not possible as integer or long, so we also return missing
        if (result.isMissing() || Double.isNaN(((DoubleValue)result).getDoubleValue())) {
            return result;
        }
        if (getColumnSpec().getType().equals(IntCell.TYPE)) {
            return new IntCell((int)Math.round(((DoubleValue)result).getDoubleValue()));
        } else {
            return new LongCell(Math.round(((DoubleValue)result).getDoubleValue()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DerivedField getPMMLDerivedField() {
        if (m_mean == null) {
            throw new IllegalStateException("The field can only be created after the statistic has been filled");
        }
        DataCell result = m_mean.getResult(0);
        if (result.isMissing()) {
            return null;
        }
        DATATYPE.Enum dt = DATATYPE.INTEGER;
        long value = Math.round(((DoubleValue)result).getDoubleValue());
        String val = Long.toString(value);

        return createValueReplacingDerivedField(dt, val);
    }
}
