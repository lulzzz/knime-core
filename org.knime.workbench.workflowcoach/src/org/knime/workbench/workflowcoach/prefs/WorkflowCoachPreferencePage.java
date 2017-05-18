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
 */
package org.knime.workbench.workflowcoach.prefs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.knime.workbench.core.KNIMECorePlugin;
import org.knime.workbench.core.preferences.HeadlessPreferencesConstants;
import org.knime.workbench.workflowcoach.NodeRecommendationManager;
import org.knime.workbench.workflowcoach.data.CommunityTripleProvider;
import org.knime.workbench.workflowcoach.data.NodeTripleProvider;
import org.knime.workbench.workflowcoach.data.UpdatableNodeTripleProvider;
import org.osgi.framework.FrameworkUtil;

/**
 * Preference page to configure the community workflow coach.
 *
 * @author Martin Horn, University of Konstanz
 */
public class WorkflowCoachPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
    /** The id of this preference page. */
    public static final String ID = "org.knime.workbench.workflowcoach";

    private Button m_checkCommunityProvider;

    private Label m_lastUpdate;

    private Button m_updateButton;

    private Button m_noAutoUpdateButton;

    private Button m_weeklyUpdateButton;

    private Button m_monthlyUpdateButton;

    /**
     * Creates a new community workflow coach preference page.
     */
    public WorkflowCoachPreferencePage() {
        super("KNIME Workflow Coach Settings");
        setDescription("The Workflow Coach helps you build your workflows more efficiently by "
            + "suggesting the next most likely node for your workflow.\n\n"
            + "Activate the checkbox 'Node Recommendations by the Community' "
            + "if you would like to receive these tips. Note – you might need "
            + "to download the statistics first: Update Now.\n\nNext, select how "
            + "often you want the statistics to be updated.\n");
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final IWorkbench workbench) {
        setPreferenceStore(
            new ScopedPreferenceStore(InstanceScope.INSTANCE, FrameworkUtil.getBundle(getClass()).getSymbolicName()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Control createContents(final Composite parent) {
        Composite composite = createComposite(parent, 1, "");

        /* from community */
        m_checkCommunityProvider = new Button(composite, SWT.CHECK);
        m_checkCommunityProvider.setText("Node Recommendations by the Community");
        m_checkCommunityProvider.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        m_checkCommunityProvider.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                if (getLastUpdate().isPresent() || !m_checkCommunityProvider.getSelection()) {
                    setValid(true);
                    setErrorMessage(null);
                } else {
                    setValid(false);
                    setErrorMessage("No community node recommendations available. Please update.");
                }
            }
        });

        if (!KNIMECorePlugin.getDefault().getPreferenceStore()
            .getBoolean(HeadlessPreferencesConstants.P_SEND_ANONYMOUS_STATISTICS)) {
            Label hint = new Label(composite, SWT.NONE);
            hint.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
            hint.setText(
                "\nImportant: By activating the community node recommendations you also automatically\nagree to "
                    + "provide your own usage statistics to the community.");
        }

        /* update */
        Composite updateComp = createComposite(composite, 3, "Automatic Update Schedule");
        m_noAutoUpdateButton = new Button(updateComp, SWT.RADIO);
        m_noAutoUpdateButton.setText("No Automatic Update");
        m_weeklyUpdateButton = new Button(updateComp, SWT.RADIO);
        m_weeklyUpdateButton.setText("Weekly");
        m_monthlyUpdateButton = new Button(updateComp, SWT.RADIO);
        m_monthlyUpdateButton.setText("Monthly");


        Composite manualUpdateComp = createComposite(updateComp, 2, null);
        GridData gd = new GridData();
        gd.horizontalSpan = 3;
        manualUpdateComp.setLayoutData(gd);

        m_updateButton = new Button(manualUpdateComp, SWT.PUSH);
        m_updateButton.setText("  Update Now  ");
        m_updateButton.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(final SelectionEvent e) {
                onUpate();
            }
        });
        m_lastUpdate = new Label(manualUpdateComp, SWT.NONE);

        initializeValues();

        return composite;
    }

    /**
     * Called when the update button has been pressed.
     */
    private void onUpate() {
        Display.getDefault().syncExec(() -> {
            m_updateButton.setEnabled(false);
            m_lastUpdate.setText("Updating ...");
            m_lastUpdate.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLACK));
            setValid(false);
        });

        //update the currently enabled providers
        List<UpdatableNodeTripleProvider> toUpdate = new ArrayList<>();
        for (NodeTripleProvider ntp : NodeRecommendationManager.getInstance().getNodeTripleProviders()) {
            if (m_checkCommunityProvider.getSelection() && (ntp instanceof CommunityTripleProvider)) {
                toUpdate.add((CommunityTripleProvider)ntp);
            }
        }

        UpdateJob.schedule(e -> {
            if (e.isPresent()) {
                Display.getDefault().syncExec(() -> {
                    m_updateButton.setEnabled(true);
                    m_lastUpdate.setText("Update failed.");
                    setErrorMessage(
                        "Error loading the community node recommendations. Please check your internet connection.");
                    if(!m_checkCommunityProvider.getSelection()) {
                        setValid(true);
                    }
                });
            } else {
                Display.getDefault().syncExec(() -> {
                    m_updateButton.setEnabled(true);
                    m_lastUpdate.setText("Update finished!");
                    setValid(true);
                    setErrorMessage(null);
                });
            }
        }, toUpdate, false);
    }

    private void initializeValues() {
        IPreferenceStore prefStore = getPreferenceStore();
        m_checkCommunityProvider
            .setSelection(prefStore.getBoolean(WorkflowCoachPreferenceInitializer.P_COMMUNITY_NODE_TRIPLE_PROVIDER));

        Optional<LocalDateTime> lastUpdate = getLastUpdate();
        if (lastUpdate.isPresent()) {
            m_lastUpdate
                .setText("Last Update: " + NodeTripleProvider.LAST_UPDATE_FORMAT.format(lastUpdate.get()));
        } else {
            m_lastUpdate.setText("Not updated, yet.");
            m_lastUpdate.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
        }

        int updateSchedule = prefStore.getInt(WorkflowCoachPreferenceInitializer.P_AUTO_UPDATE_SCHEDULE);
        switch (updateSchedule) {
            case WorkflowCoachPreferenceInitializer.WEEKLY_UPDATE:
                m_weeklyUpdateButton.setSelection(true);
                break;
            case WorkflowCoachPreferenceInitializer.MONTHLY_UPDATE:
                m_monthlyUpdateButton.setSelection(true);
                break;
            case WorkflowCoachPreferenceInitializer.NO_AUTO_UPDATE:
            default:
                m_noAutoUpdateButton.setSelection(true);
        }
    }

    /**
     * @return the last update date or an empty {@link Optional} if there wasn't an update so far (i.e. the community
     *         statistic file doesn't exist)
     */
    private Optional<LocalDateTime> getLastUpdate() {
        Optional<Optional<LocalDateTime>> lastUpdate = NodeRecommendationManager.getInstance().getNodeTripleProviders()
            .stream().filter(p -> p instanceof CommunityTripleProvider).map(p -> p.getLastUpdate()).findFirst();
        if (lastUpdate.isPresent()) {
            return lastUpdate.get();
        } else {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performOk() {
        IPreferenceStore prefStore = getPreferenceStore();
        List<NodeTripleProvider> providers = NodeRecommendationManager.getInstance().getNodeTripleProviders();

        //check whether the selected providers need an update
        for (NodeTripleProvider ntp : providers) {
            if (ntp instanceof CommunityTripleProvider) {
                if (m_checkCommunityProvider.getSelection() && ((CommunityTripleProvider)ntp).updateRequired()) {
                    setErrorMessage("Please update the community node usage statistics.");
                    return false;
                }
            }
        }

        if (m_checkCommunityProvider.getSelection()) {
            //make sure that the user statistics are sent when the community node recommendation is used
            KNIMECorePlugin.getDefault().getPreferenceStore()
                .setValue(HeadlessPreferencesConstants.P_SEND_ANONYMOUS_STATISTICS, true);
        }
        //store values
        prefStore.setValue(WorkflowCoachPreferenceInitializer.P_COMMUNITY_NODE_TRIPLE_PROVIDER,
            m_checkCommunityProvider.getSelection());

        if (m_noAutoUpdateButton.getSelection()) {
            prefStore.setValue(WorkflowCoachPreferenceInitializer.P_AUTO_UPDATE_SCHEDULE,
                WorkflowCoachPreferenceInitializer.NO_AUTO_UPDATE);
        } else if (m_weeklyUpdateButton.getSelection()) {
            prefStore.setValue(WorkflowCoachPreferenceInitializer.P_AUTO_UPDATE_SCHEDULE, WorkflowCoachPreferenceInitializer.WEEKLY_UPDATE);
        } else if (m_monthlyUpdateButton.getSelection()) {
            prefStore.setValue(WorkflowCoachPreferenceInitializer.P_AUTO_UPDATE_SCHEDULE,
                WorkflowCoachPreferenceInitializer.MONTHLY_UPDATE);
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void performDefaults() {
        //restore default values
        IPreferenceStore prefStore = getPreferenceStore();
        m_checkCommunityProvider
            .setSelection(prefStore.getDefaultBoolean(WorkflowCoachPreferenceInitializer.P_COMMUNITY_NODE_TRIPLE_PROVIDER));
        int autoUpdate = prefStore.getDefaultInt(WorkflowCoachPreferenceInitializer.P_AUTO_UPDATE_SCHEDULE);
        switch (autoUpdate) {
            case WorkflowCoachPreferenceInitializer.NO_AUTO_UPDATE:
                m_noAutoUpdateButton.setSelection(true);
                break;
            case WorkflowCoachPreferenceInitializer.WEEKLY_UPDATE:
                m_weeklyUpdateButton.setSelection(true);
                break;
            case WorkflowCoachPreferenceInitializer.MONTHLY_UPDATE:
                m_monthlyUpdateButton.setSelection(true);
                break;
            default:
                break;
        }
    }

    /**
     * Create a new composite. If the title is non-null it will be a group (i.e. with border and title).
     */
    private Composite createComposite(final Composite parent, final int numColumns, final String title) {
        Composite composite;
        GridLayout layout = new GridLayout();

        if (!StringUtils.isEmpty(title)) {
            Group group = new Group(parent, SWT.NULL);
            group.setText(title);
            composite = group;
            layout.marginWidth = 10;
            layout.marginHeight = 10;
        } else {
            composite = new Composite(parent, SWT.NULL);
        }
        layout.numColumns = numColumns;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        return composite;
    }
}
