package org.monjasa.interpreter.engine.ast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class CompoundStatementNode extends AbstractNode {

    private ArrayList<AbstractNode> childNodes;

    public CompoundStatementNode() {
        childNodes = new ArrayList<>();
    }

    public void appendChildNode(AbstractNode node) {
        childNodes.add(node);
    }

    public ArrayList<AbstractNode> getChildNodes() {
        return childNodes;
    }

    @Override
    public String toString() {

        String result = childNodes.stream()
                .map(node -> node.toString())
                .collect(Collectors.joining(",\n\t"));

        return String.format(Locale.US, "%s: {%s}", super.toString(), result);
    }
}
