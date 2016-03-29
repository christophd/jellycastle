package org.jellycastle.maven;

import org.w3c.dom.*;

/**
 * @author Christoph Deppisch
 * @since 1.0
 */
public class PropertyElement implements Element {
    private String name;
    private String value;

    public PropertyElement(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getTagName() {
        return name;
    }

    @Override
    public String getAttribute(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAttribute(String name, String value) throws DOMException {
    }

    @Override
    public void removeAttribute(String name) throws DOMException {
    }

    @Override
    public Attr getAttributeNode(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attr setAttributeNode(Attr newAttr) throws DOMException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attr removeAttributeNode(Attr oldAttr) throws DOMException {
        throw new UnsupportedOperationException();
    }

    @Override
    public NodeList getElementsByTagName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getAttributeNS(String namespaceURI, String localName) throws DOMException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAttributeNS(String namespaceURI, String qualifiedName, String value) throws DOMException {
    }

    @Override
    public void removeAttributeNS(String namespaceURI, String localName) throws DOMException {
    }

    @Override
    public Attr getAttributeNodeNS(String namespaceURI, String localName) throws DOMException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Attr setAttributeNodeNS(Attr newAttr) throws DOMException {
        throw new UnsupportedOperationException();
    }

    @Override
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) throws DOMException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasAttribute(String name) {
        return false;
    }

    @Override
    public boolean hasAttributeNS(String namespaceURI, String localName) throws DOMException {
        return false;
    }

    @Override
    public TypeInfo getSchemaTypeInfo() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIdAttribute(String name, boolean isId) throws DOMException {
    }

    @Override
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) throws DOMException {
    }

    @Override
    public void setIdAttributeNode(Attr idAttr, boolean isId) throws DOMException {
    }

    @Override
    public String getNodeName() {
        return name;
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
        return Node.ELEMENT_NODE;
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
        return new PropertyTextValue(value);
    }

    @Override
    public Node getLastChild() {
        return null;
    }

    @Override
    public Node getPreviousSibling() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Node getNextSibling() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NamedNodeMap getAttributes() {
        return new EmptyAttributes();
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
        return name;
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

    private class EmptyAttributes implements NamedNodeMap {
        @Override
        public Node getNamedItem(String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Node setNamedItem(Node arg) throws DOMException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Node removeNamedItem(String name) throws DOMException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Node item(int index) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getLength() {
            return 0;
        }

        @Override
        public Node getNamedItemNS(String namespaceURI, String localName) throws DOMException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Node setNamedItemNS(Node arg) throws DOMException {
            throw new UnsupportedOperationException();
        }

        @Override
        public Node removeNamedItemNS(String namespaceURI, String localName) throws DOMException {
            throw new UnsupportedOperationException();
        }
    }
}
