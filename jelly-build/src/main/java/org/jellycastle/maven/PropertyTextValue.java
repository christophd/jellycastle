package org.jellycastle.maven;

import org.w3c.dom.*;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
public class PropertyTextValue implements Node {
    private String value;

    public PropertyTextValue(String value) {
        this.value = value;
    }

    @Override
    public String getNodeName() {
        return null;
    }

    @Override
    public String getNodeValue() throws DOMException {
        return value;
    }

    @Override
    public void setNodeValue(String nodeValue) throws DOMException {
    }

    @Override
    public short getNodeType() {
        return Node.TEXT_NODE;
    }

    @Override
    public Node getParentNode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NodeList getChildNodes() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node getFirstChild() {
        return null;
    }

    @Override
    public Node getLastChild() {
        return null;
    }

    @Override
    public Node getPreviousSibling() {
        return null;
    }

    @Override
    public Node getNextSibling() {
        return null;
    }

    @Override
    public NamedNodeMap getAttributes() {
        return null;
    }

    @Override
    public Document getOwnerDocument() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node insertBefore(Node newChild, Node refChild) throws DOMException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node removeChild(Node oldChild) throws DOMException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node appendChild(Node newChild) throws DOMException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasChildNodes() {
        return true;
    }

    @Override
    public Node cloneNode(boolean deep) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void normalize() {
    }

    @Override
    public boolean isSupported(String feature, String version) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getNamespaceURI() {
        return "http://maven.apache.org/POM/4.0.0";
    }

    @Override
    public String getPrefix() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPrefix(String prefix) throws DOMException {
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public boolean hasAttributes() {
        return false;
    }

    @Override
    public String getBaseURI() {
        throw new UnsupportedOperationException();
    }

    @Override
    public short compareDocumentPosition(Node other) throws DOMException {
        return 0;
    }

    @Override
    public String getTextContent() throws DOMException {
        return value;
    }

    @Override
    public void setTextContent(String textContent) throws DOMException {
    }

    @Override
    public boolean isSameNode(Node other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String lookupPrefix(String namespaceURI) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDefaultNamespace(String namespaceURI) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String lookupNamespaceURI(String prefix) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEqualNode(Node arg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getFeature(String feature, String version) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getUserData(String key) {
        throw new UnsupportedOperationException();
    }
}
