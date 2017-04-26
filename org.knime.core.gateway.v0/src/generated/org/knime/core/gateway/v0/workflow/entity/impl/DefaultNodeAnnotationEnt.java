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
 */
package org.knime.core.gateway.v0.workflow.entity.impl;

import org.knime.core.gateway.v0.workflow.entity.AnnotationEnt;
import org.knime.core.gateway.v0.workflow.entity.NodeAnnotationEnt;
import org.knime.core.gateway.v0.workflow.entity.builder.NodeAnnotationEntBuilder;

import org.knime.core.gateway.entities.EntityBuilderFactory;
import org.knime.core.gateway.entities.EntityBuilderManager;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Default implementation of the NodeAnnotationEnt-interface. E.g. used if no other {@link EntityBuilderFactory}
 * implementation (provided via the respective extension point, see {@link EntityBuilderManager}) is available.
 *
 * @author Martin Horn, University of Konstanz
 */
public class DefaultNodeAnnotationEnt implements NodeAnnotationEnt {

	private boolean m_IsDefault;
	private String m_Text;
	private int m_BackgroundColor;
	private int m_X;
	private int m_Y;
	private int m_Width;
	private int m_Height;
	private String m_TextAlignment;
	private int m_BorderSize;
	private int m_BorderColor;
	private int m_DefaultFontSize;
	private int m_Version;

    /**
     * @param builder
     */
    private DefaultNodeAnnotationEnt(final DefaultNodeAnnotationEntBuilder builder) {
		m_IsDefault = builder.m_IsDefault;
		m_Text = builder.m_Text;
		m_BackgroundColor = builder.m_BackgroundColor;
		m_X = builder.m_X;
		m_Y = builder.m_Y;
		m_Width = builder.m_Width;
		m_Height = builder.m_Height;
		m_TextAlignment = builder.m_TextAlignment;
		m_BorderSize = builder.m_BorderSize;
		m_BorderColor = builder.m_BorderColor;
		m_DefaultFontSize = builder.m_DefaultFontSize;
		m_Version = builder.m_Version;
    }

	@Override
    public boolean getIsDefault() {
        return m_IsDefault;
    }
    
	@Override
    public String getText() {
        return m_Text;
    }
    
	@Override
    public int getBackgroundColor() {
        return m_BackgroundColor;
    }
    
	@Override
    public int getX() {
        return m_X;
    }
    
	@Override
    public int getY() {
        return m_Y;
    }
    
	@Override
    public int getWidth() {
        return m_Width;
    }
    
	@Override
    public int getHeight() {
        return m_Height;
    }
    
	@Override
    public String getTextAlignment() {
        return m_TextAlignment;
    }
    
	@Override
    public int getBorderSize() {
        return m_BorderSize;
    }
    
	@Override
    public int getBorderColor() {
        return m_BorderColor;
    }
    
	@Override
    public int getDefaultFontSize() {
        return m_DefaultFontSize;
    }
    
	@Override
    public int getVersion() {
        return m_Version;
    }
    

	@Override
	public String toString() {
	    return ToStringBuilder.reflectionToString(this);
	}

	public static DefaultNodeAnnotationEntBuilder builder() {
		return new DefaultNodeAnnotationEntBuilder();
	}
	
	/**
	* Default implementation of the NodeAnnotationEntBuilder-interface.
	*/
	public static class DefaultNodeAnnotationEntBuilder implements NodeAnnotationEntBuilder {
    
		private boolean m_IsDefault;
		private String m_Text;
		private int m_BackgroundColor;
		private int m_X;
		private int m_Y;
		private int m_Width;
		private int m_Height;
		private String m_TextAlignment;
		private int m_BorderSize;
		private int m_BorderColor;
		private int m_DefaultFontSize;
		private int m_Version;

        public NodeAnnotationEnt build() {
            return new DefaultNodeAnnotationEnt(this);
        }

		@Override
        public NodeAnnotationEntBuilder setIsDefault(final boolean IsDefault) {
			m_IsDefault = IsDefault;			
            return this;
        }
        
		@Override
        public NodeAnnotationEntBuilder setText(final String Text) {
			m_Text = Text;			
            return this;
        }
        
		@Override
        public NodeAnnotationEntBuilder setBackgroundColor(final int BackgroundColor) {
			m_BackgroundColor = BackgroundColor;			
            return this;
        }
        
		@Override
        public NodeAnnotationEntBuilder setX(final int X) {
			m_X = X;			
            return this;
        }
        
		@Override
        public NodeAnnotationEntBuilder setY(final int Y) {
			m_Y = Y;			
            return this;
        }
        
		@Override
        public NodeAnnotationEntBuilder setWidth(final int Width) {
			m_Width = Width;			
            return this;
        }
        
		@Override
        public NodeAnnotationEntBuilder setHeight(final int Height) {
			m_Height = Height;			
            return this;
        }
        
		@Override
        public NodeAnnotationEntBuilder setTextAlignment(final String TextAlignment) {
			m_TextAlignment = TextAlignment;			
            return this;
        }
        
		@Override
        public NodeAnnotationEntBuilder setBorderSize(final int BorderSize) {
			m_BorderSize = BorderSize;			
            return this;
        }
        
		@Override
        public NodeAnnotationEntBuilder setBorderColor(final int BorderColor) {
			m_BorderColor = BorderColor;			
            return this;
        }
        
		@Override
        public NodeAnnotationEntBuilder setDefaultFontSize(final int DefaultFontSize) {
			m_DefaultFontSize = DefaultFontSize;			
            return this;
        }
        
		@Override
        public NodeAnnotationEntBuilder setVersion(final int Version) {
			m_Version = Version;			
            return this;
        }
        
    }
}
