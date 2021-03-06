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
 */
package org.knime.base.node.image.writepng;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataType;
import org.knime.core.data.image.ImageContent;
import org.knime.core.data.image.ImageValue;
import org.knime.core.data.image.png.PNGImageContent;
import org.knime.core.data.image.png.PNGImageValue;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.core.node.port.PortObject;
import org.knime.core.node.port.PortObjectSpec;
import org.knime.core.node.port.PortType;
import org.knime.core.node.port.image.ImagePortObject;
import org.knime.core.node.port.image.ImagePortObjectSpec;
import org.knime.core.util.FileUtil;

/**
 *
 * @author Bernd Wiswedel, KNIME.com, Zurich, Switzerland
 */
final class WritePNGNodeModel extends NodeModel {

    private SettingsModelString m_fileOutSettings;

    private SettingsModelBoolean m_overwriteOKBoolean;

    /**
     *
     */
    WritePNGNodeModel() {
        super(new PortType[]{ImagePortObject.TYPE}, new PortType[0]);
        m_fileOutSettings = createFileOutSettings();
        m_overwriteOKBoolean = createOverwriteOKSettings();
    }

    /** {@inheritDoc} */
    @Override
    protected PortObjectSpec[] configure(final PortObjectSpec[] inSpecs)
            throws InvalidSettingsException {
        String outPath = m_fileOutSettings.getStringValue();
        boolean overwriteOK = m_overwriteOKBoolean.getBooleanValue();
        if (outPath == null || outPath.length() == 0) {
            throw new InvalidSettingsException("No output path specified");
        }
        File f = new File(outPath);
        if (f.isDirectory()) {
            throw new InvalidSettingsException("Can't write to \"" + outPath
                    + "\": it is a directory");
        }
        if (f.isFile() && !overwriteOK) {
            throw new InvalidSettingsException("Can't write to \"" + outPath
                    + "\": file exists (configure overwrite in dialog)");
        }
        File directory = f.getParentFile();
        if (directory == null || !directory.isDirectory()) {
            throw new InvalidSettingsException("Can't write to \"" + outPath
                    + "\": parent directory does not exist");
        }
        ImagePortObjectSpec imageInObject = (ImagePortObjectSpec)inSpecs[0];
        DataType dataType = imageInObject.getDataType();
        if (!dataType.isCompatible(PNGImageValue.class)) {
            throw new InvalidSettingsException("Unsupported image type, "
                    + "expecting PNG input, got " + dataType);
        }
        return new PortObjectSpec[0];
    }

    /** {@inheritDoc} */
    @Override
    protected PortObject[] execute(final PortObject[] inObjects,
            final ExecutionContext exec) throws Exception {
        String outPath = m_fileOutSettings.getStringValue();
        ImagePortObject imageObj = (ImagePortObject)inObjects[0];
        DataCell imageCellDC = imageObj.toDataCell();
        if (!(imageCellDC instanceof ImageValue)) {
            throw new InvalidSettingsException("Image object does not produce"
                    + " valid image object but " + imageCellDC == null ? null
                    : imageCellDC.getClass().getName());
        }
        // overwrite check done in configure()
        ImageValue v = (ImageValue)imageCellDC;
        ImageContent content = v.getImageContent();
        if (content instanceof PNGImageContent) {
            byte[] bytes = ((PNGImageContent)content).getByteArrayReference();
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            FileOutputStream out = new FileOutputStream(new File(outPath));
            FileUtil.copy(in, out);
            in.close();
            out.close();
        } else {
            throw new InvalidSettingsException("Unsupported image type: "
                    + content.getClass().getName() + " (expected PNG)");
        }
        return new PortObject[0];
    }

    /** {@inheritDoc} */
    @Override
    protected void reset() {
        // no op
    }

    /** {@inheritDoc} */
    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {
        m_fileOutSettings.saveSettingsTo(settings);
        m_overwriteOKBoolean.saveSettingsTo(settings);
    }

    /** {@inheritDoc} */
    @Override
    protected void validateSettings(final NodeSettingsRO settings)
            throws InvalidSettingsException {
        m_fileOutSettings.validateSettings(settings);
        m_overwriteOKBoolean.validateSettings(settings);
    }

    /** {@inheritDoc} */
    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
            throws InvalidSettingsException {
        m_fileOutSettings.loadSettingsFrom(settings);
        m_overwriteOKBoolean.loadSettingsFrom(settings);
    }

    /** {@inheritDoc} */
    @Override
    protected void loadInternals(final File nodeInternDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
        // no op
    }

    /** {@inheritDoc} */
    @Override
    protected void saveInternals(final File nodeInternDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
        // no op
    }

    /** Create settings for file output path.
     * @return New settings object.
     */
    static final SettingsModelString createFileOutSettings() {
        return new SettingsModelString("file_out_path", null);
    }

    /** Create settings for overwrite OK flag.
     * @return New settings object.
     */
    static final SettingsModelBoolean createOverwriteOKSettings() {
        return new SettingsModelBoolean("overwrite_ok", false);
    }

}
