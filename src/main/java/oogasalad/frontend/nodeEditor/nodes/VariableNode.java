package oogasalad.frontend.nodeEditor.nodes;

import io.github.eckig.grapheditor.model.GConnector;
import io.github.eckig.grapheditor.model.GNode;
import oogasalad.frontend.nodeEditor.skins.tree.TreeNodeSkin;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.resource.Resource;

import java.lang.reflect.InvocationTargetException;

public class VariableNode extends ooglaNode{

    private GNode myVariableNode;

    /**
     * Creates a new {@link TreeNodeSkin} instance.
     *
     * @param node the {link GNode} this skin is representing
     */
    public VariableNode(GNode node) {

        myVariableNode = new GNode() {
            @Override
            public String getId() {
                return null;
            }

            @Override
            public void setId(String value) {

            }

            @Override
            public String getType() {
                return null;
            }

            @Override
            public void setType(String value) {

            }

            @Override
            public double getX() {
                return 0;
            }

            @Override
            public void setX(double value) {

            }

            @Override
            public double getY() {
                return 0;
            }

            @Override
            public void setY(double value) {

            }

            @Override
            public double getWidth() {
                return 0;
            }

            @Override
            public void setWidth(double value) {

            }

            @Override
            public double getHeight() {
                return 0;
            }

            @Override
            public void setHeight(double value) {

            }

            @Override
            public EList<GConnector> getConnectors() {
                return null;
            }

            @Override
            public EClass eClass() {
                return null;
            }

            @Override
            public Resource eResource() {
                return null;
            }

            @Override
            public EObject eContainer() {
                return null;
            }

            @Override
            public EStructuralFeature eContainingFeature() {
                return null;
            }

            @Override
            public EReference eContainmentFeature() {
                return null;
            }

            @Override
            public EList<EObject> eContents() {
                return null;
            }

            @Override
            public TreeIterator<EObject> eAllContents() {
                return null;
            }

            @Override
            public boolean eIsProxy() {
                return false;
            }

            @Override
            public EList<EObject> eCrossReferences() {
                return null;
            }

            @Override
            public Object eGet(EStructuralFeature eStructuralFeature) {
                return null;
            }

            @Override
            public Object eGet(EStructuralFeature eStructuralFeature, boolean b) {
                return null;
            }

            @Override
            public void eSet(EStructuralFeature eStructuralFeature, Object o) {

            }

            @Override
            public boolean eIsSet(EStructuralFeature eStructuralFeature) {
                return false;
            }

            @Override
            public void eUnset(EStructuralFeature eStructuralFeature) {

            }

            @Override
            public Object eInvoke(EOperation eOperation, EList<?> eList) throws InvocationTargetException {
                return null;
            }

            @Override
            public EList<Adapter> eAdapters() {
                return null;
            }

            @Override
            public boolean eDeliver() {
                return false;
            }

            @Override
            public void eSetDeliver(boolean b) {

            }

            @Override
            public void eNotify(Notification notification) {

            }
        };

    }
}
