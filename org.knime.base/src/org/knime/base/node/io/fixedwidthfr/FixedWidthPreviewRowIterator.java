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
 *   17.10.2014 (tibuch): created
 */
package org.knime.base.node.io.fixedwidthfr;

import java.io.IOException;

import org.knime.base.node.io.filereader.FileReaderException;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.node.ExecutionContext;

/**
 *
 * @author Tim-Oliver Buchholz
 */
public class FixedWidthPreviewRowIterator extends FixedWidthRowIterator {

    private FixedWidthFRPreviewTable m_table;

    // we end it after an error occurred, this flag indicates that.
    private boolean m_done;

    /**
     * A new non-failing file row iterator. It wraps the passed iterator and catches its exceptions, converting them
     * into rows with error messages.
     *
     * @param nodeSettings the node settings
     * @param tableSpec the tableSped
     * @param exec the execution context
     * @param previewTable the corresponding table this is the iterator from. Needed to pull the sprc from for creating
     *            the row in case of an error/exception and to tell the table an error occurred.
     * @throws IOException thrown if no buffered input reader can be created
     */
    FixedWidthPreviewRowIterator(final FixedWidthFRSettings nodeSettings, final DataTableSpec tableSpec,
        final ExecutionContext exec, final FixedWidthFRPreviewTable previewTable) throws IOException {
        super(nodeSettings, tableSpec, exec);
        m_table = previewTable;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasNext() {
        return !m_done && super.hasNext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DataRow next() {
        DataRow nextRow = null;

        try {
            nextRow = super.next();
        } catch (FileReaderException fre) {
            // after an error occurred all missing elements read would be
            // junk. Thus we end the table after the first exception.
            m_done = true;
            m_table.setError(fre);
            nextRow = fre.getErrorRow();

        }

        return nextRow;
    }
}
