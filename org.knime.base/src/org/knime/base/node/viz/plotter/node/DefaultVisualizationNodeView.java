/* -------------------------------------------------------------------
 * This source code, its documentation and all appendant files
 * are protected by copyright law. All rights reserved.
 * 
 * Copyright, 2003 - 2006
 * Universitaet Konstanz, Germany.
 * Lehrstuhl fuer Angewandte Informatik
 * Prof. Dr. Michael R. Berthold
 * 
 * You may not modify, publish, transmit, transfer or sell, reproduce,
 * create derivative works from, distribute, perform, display, or in
 * any way exploit any of the content, in whole or in part, except as
 * otherwise expressly permitted in writing by the copyright owner.
 * -------------------------------------------------------------------
 * 
 * History
 *   30.08.2006 (Fabian Dill): created
 */
package org.knime.base.node.viz.plotter.node;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import org.knime.base.node.viz.plotter.AbstractPlotter;
import org.knime.base.node.viz.plotter.DataProvider;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeView;
import org.knime.core.node.property.hilite.HiLiteHandler;

/**
 * Convenient implementation of a {@link org.knime.core.node.NodeView} that 
 * can display one or more plotter implementations. One plotter 
 * implementation has to be passed to the constructor and additional plotters
 * can be added as tabs with 
 * {@link #addVisualization(AbstractPlotter, String)}. The appropriate 
 * update methods are called by this class for all added plotters.
 * 
 * @author Fabian Dill, University of Konstanz
 */
public class DefaultVisualizationNodeView extends NodeView {
    
    private JTabbedPane m_tabs;
    
    private List<AbstractPlotter>m_plotters;

    private int m_plotterCounter = 1;
    
    
     
    
    /**
     * A generic {@link org.knime.core.node.NodeView} which sets the model and 
     * calls the right methods of the plotters.
     * 
     * @param model the node model (must implement DataProvider).
     * @param plotter the plotter
     */
    public DefaultVisualizationNodeView(final NodeModel model, 
            final AbstractPlotter plotter) {
        super(model);
        if (!(model instanceof DataProvider)) {
            throw new IllegalArgumentException(
                    "Model must implement the DataProvider interface!");
        }
        m_plotters = new ArrayList<AbstractPlotter>();
        m_plotters.add(plotter);
        plotter.setDataProvider((DataProvider)model);
        plotter.setHiLiteHandler(model.getInHiLiteHandler(0));
//        plotter.updatePaintModel();
        getJMenuBar().add(plotter.getHiLiteMenu());
        setComponent(plotter);
    }
    
    /**
     * A generic {@link org.knime.core.node.NodeView} which sets the model and 
     * calls the right methods of the plotters the title is the title of the 
     * according tab.
     * 
     * @param model the node model (must implement DataProvider).
     * @param plotter the plotter
     * @param title the title for the first tab
     */
    public DefaultVisualizationNodeView(final NodeModel model, 
            final AbstractPlotter plotter, final String title) {
        super(model);
        if (!(model instanceof DataProvider)) {
            throw new IllegalArgumentException(
                    "Model must implement the DataProvider interface!");
        }
        m_plotters = new ArrayList<AbstractPlotter>();
        m_plotters.add(plotter);
        plotter.setDataProvider((DataProvider)model);
        plotter.setHiLiteHandler(model.getInHiLiteHandler(0));
        m_tabs = new JTabbedPane();
        m_tabs.addTab(title, plotter);
        setComponent(m_tabs);
    }    
    
    /**
     * Adds another tab with title <code>title</code> containing a plotter.
     * @param plotter another visualization
     * @param title the title of the tab (if null a standard name is provided)
     */
    public void addVisualization(final AbstractPlotter plotter, 
            final String title) {
        m_plotterCounter++;
        String name = title;
        if (name == null) {
            name = "Visualization#" + m_plotterCounter;
        }
        // check if there is already a tab
        if (m_tabs == null) {
            m_tabs = new JTabbedPane();
            AbstractPlotter oldPlotter = m_plotters.get(1);
            m_tabs.addTab("Visualization#1", oldPlotter);
            m_tabs.addTab(name, plotter);
            setComponent(m_tabs);
        } else {
            m_tabs.addTab(name, plotter);
        }
        m_plotters.add(plotter);
    }

    /**
     * @see org.knime.core.node.NodeView#modelChanged()
     */
    @Override
    protected void modelChanged() {
        NodeModel model = getNodeModel();
        if (model == null) {
            return;
        }
        if (!(model instanceof DataProvider)) {
            throw new IllegalArgumentException(
                    "Model must implement the DataProvider interface!");
        }
        DataProvider provider = (DataProvider)model;
        HiLiteHandler hiliteHandler = model.getInHiLiteHandler(0);
        boolean useAntiAlias = false;
        if (model instanceof DefaultVisualizationNodeModel) {
            useAntiAlias = ((DefaultVisualizationNodeModel)model)
                .antiAliasingOn();
        }
        if (m_plotters != null) {
            for (AbstractPlotter plotter : m_plotters) {
                plotter.reset();
                plotter.setHiLiteHandler(hiliteHandler);
                plotter.setAntialiasing(useAntiAlias);
                plotter.setDataProvider(provider);
                plotter.updatePaintModel();
            }
        }
    }

    /**
     * @see org.knime.core.node.NodeView#onClose()
     */
    @Override
    protected void onClose() {
    }

    /**
     * @see org.knime.core.node.NodeView#onOpen()
     */
    @Override
    protected void onOpen() {
    }

}
