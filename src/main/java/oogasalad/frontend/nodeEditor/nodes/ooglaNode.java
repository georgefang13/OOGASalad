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

public class ooglaNode implements GNode {

    private GNode myOoglaNode;
    private String id;
    private String myType;
    private double x;
    private double y;
    private double width;
    private double height;
    private EList<GConnector> connectors;



    public ooglaNode(GNode node) {
//        myOoglaNode;
    }

    public ooglaNode() {

    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String value) {
        id = value;

    }

    @Override
    public String getType() {
        return myType;
    }

    @Override
    public void setType(String value) {
        myType = value;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double value) {
        x = value;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double value) {
        y = value;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public void setWidth(double value) {
        width = value;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setHeight(double value) {
        height = value;
    }

    @Override
    public EList<GConnector> getConnectors() {
        return connectors;
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
}
