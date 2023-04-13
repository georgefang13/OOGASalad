//package oogasalad.frontend.panels;
//
//import oogasalad.frontend.panels.subPanels.HeaderMenuPanel;
//
//public class PanelFactory {
//    private enum PanelType {
//        HEADER_MENU,
//
//    }
//
//    public static Panel createPanel(PanelType panelType, String panelID, PanelController panelController) {
//        switch (panelType) {
//            case HEADER_MENU:
//                Panel headerMenu = new HeaderMenuPanel(panelController);
//                panelController.addPanel(panelID,headerMenu);
//                return headerMenu;
//            default:
//                throw new IllegalArgumentException("Invalid panel type: " + panelType);
//        }
//    }
//}
