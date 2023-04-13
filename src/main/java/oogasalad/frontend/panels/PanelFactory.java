package oogasalad.frontend.panels;

import oogasalad.frontend.panels.subPanels.HeaderMenuPanel;
import oogasalad.frontend.panels.subPanels.PropertiesPanel;

public class PanelFactory {
    private enum PanelType {
        HEADER_MENU,
        PROPERTIES
    }

    public static Panel createPanel(PanelType panelType, String panelID, PanelController panelController) {
        switch (panelType) {
            case HEADER_MENU:
                Panel headerMenu = new HeaderMenuPanel(panelController);
                panelController.addPanel(panelID,headerMenu);
                return headerMenu;
            case PROPERTIES:
                Panel properties = new PropertiesPanel(panelController);
                panelController.addPanel(panelID,properties);
                return properties;
            default:
                throw new IllegalArgumentException("Invalid panel type: " + panelType);
        }
    }
}
