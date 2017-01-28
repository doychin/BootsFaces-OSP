/**
 *  Copyright 2014 - 17 by Riccardo Massera (TheCoder4.Eu) and Stephan Rauh (http://www.beyondjava.net).
 *
 *  This file is part of BootsFaces.
 *
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
 */

package net.bootsfaces.component.alert;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import net.bootsfaces.render.CoreRenderer;
import net.bootsfaces.render.Responsive;
import net.bootsfaces.render.Tooltip;

/** This class generates the HTML code of &lt;b:alert /&gt;. */
@FacesRenderer(componentFamily = "net.bootsfaces.component", rendererType = "net.bootsfaces.component.alert.Alert")
public class AlertRenderer extends CoreRenderer {

	/**
	 * This methods generates the HTML code of the current b:alert.
	 * <code>encodeBegin</code> generates the start of the component. After the,
	 * the JSF framework calls <code>encodeChildren()</code> to generate the
	 * HTML code between the beginning and the end of the component. For
	 * instance, in the case of a panel component the content of the panel is
	 * generated by <code>encodeChildren()</code>. After that,
	 * <code>encodeEnd()</code> is called to generate the rest of the HTML code.
	 *
	 * @param context
	 *            the FacesContext.
	 * @param component
	 *            the current b:alert.
	 * @throws IOException
	 *             thrown if something goes wrong when writing the HTML code.
	 */
	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered()) {
			return;
		}
		Alert alert = (Alert) component;
		ResponseWriter rw = context.getResponseWriter();
		String clientId = alert.getClientId();
		// Map<String, Object> attrs = alert.getAttributes();

		String sev = alert.getSeverity();

		String t = alert.getTitle();
		boolean closbl = alert.isClosable();

		rw.startElement("div", alert);
		rw.writeAttribute("id", clientId, "id");
		Tooltip.generateTooltip(context, component, rw);

		String style = alert.getStyle();
		if (null != style)
			rw.writeAttribute("style", style, "style");

		String styleClass = alert.getStyleClass();
		if (null == styleClass)
			styleClass = "";
		else
			styleClass = " " + styleClass;

		styleClass += Responsive.getResponsiveStyleClass(alert, false);

		if (sev != null) {
			rw.writeAttribute("class", "alert alert-" + sev + " fadein" + styleClass, "class");
		} else {
			rw.writeAttribute("class", "alert fadein" + styleClass, "class");
		}
		if (closbl) {
			rw.startElement("button", alert);
			rw.writeAttribute("type", "button", "type");
			rw.writeAttribute("class", "close", "class");
			rw.writeAttribute("data-dismiss", "alert", "data-dismiss");
			rw.write("&times;");
			rw.endElement("button");
		}
		if (t != null) {
			rw.startElement("strong", alert);
			rw.writeText(t, null);
			rw.endElement("strong");
			rw.startElement("br", alert);
			rw.endElement("br");
		}
		if (sev != null) {
			rw.startElement("span", alert);
			String s="bficon-";
			if(sev.equals("success")) {s+="ok";}
			if(sev.equals("info")) {s+="info";}
			if(sev.equals("warning")) {s+="warning-triangle-o";}
			if(sev.equals("danger")) {s+="error-circle-o";}
			rw.writeAttribute("class", "bficon "+s, "class");
			rw.endElement("span");
		}
	}

	/**
	 * This methods generates the HTML code of the current b:alert.
	 * <code>encodeBegin</code> generates the start of the component. After the,
	 * the JSF framework calls <code>encodeChildren()</code> to generate the
	 * HTML code between the beginning and the end of the component. For
	 * instance, in the case of a panel component the content of the panel is
	 * generated by <code>encodeChildren()</code>. After that,
	 * <code>encodeEnd()</code> is called to generate the rest of the HTML code.
	 *
	 * @param context
	 *            the FacesContext.
	 * @param component
	 *            the current b:alert.
	 * @throws IOException
	 *             thrown if something goes wrong when writing the HTML code.
	 */
	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		if (!component.isRendered()) {
			return;
		}
		Alert alert = (Alert) component;
		ResponseWriter rw = context.getResponseWriter();
		rw.endElement("div");
		Tooltip.activateTooltips(context, alert);
	}
}
