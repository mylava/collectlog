package com.ruwe.collectlog.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipengfei on 2017/6/1.
 */
@Deprecated
public class InvokeTree {
    static ThreadLocal<InvokeTree> localTree = new ThreadLocal<InvokeTree>();

    public static class InvokeNode {
        public InvokeNode(int deep) {
            this.deep = deep;
            this.invokeCount = 1;
        }
        public InvokeNode parentNode;
        public List<InvokeNode> childNodes;
        public String invokeMethod;
        public int deep;
        public int invokeCount;
        public boolean equals(InvokeNode other) {
            if (other == null) {
                return false;
            }
            if (!invokeMethod.equals(other.invokeMethod)) {
                return false;
            }
            if (childNodes == null && other.childNodes == null) {
                return true;
            }
            if (childNodes == null || other.childNodes == null) {
                return false;
            }
            int size = childNodes.size();
            if (size != other.childNodes.size()) {
                return false;
            }

            for (int i = 0; i < size; i++) {
                InvokeNode x = childNodes.get(i);
                InvokeNode y = other.childNodes.get(i);
                if (!x.equals(y)) {
                    return false;
                }
            }
            return true;
        }
    }


    public static void start(String invokeMethod) {
//        ClientConfig clientConfig = Doom.getClientConfig();
//        if (!clientConfig.isOpenInvokeTreeAnalyse()) {
//            return;
//        }
        InvokeTree tree = new InvokeTree();
        InvokeNode rootNode = new InvokeNode(0);
        rootNode.invokeMethod = invokeMethod;
        tree.rootNode = rootNode;
        tree.curNode = rootNode;
        localTree.set(tree);
    }


    public static void exit() {
        InvokeTree tree = localTree.get();
        if (tree != null) {
            tree.curNode = tree.curNode.parentNode;
        }
    }

    public static void enter(String invokeMethod) {
        InvokeTree tree = localTree.get();
        if (tree == null) {
            return;
        }
        InvokeNode parentNode = tree.curNode;
        InvokeNode newNode = new InvokeNode(parentNode.deep + 1);
        newNode.invokeMethod = invokeMethod;
        newNode.parentNode = parentNode;
        if (parentNode.childNodes == null) {
            parentNode.childNodes = new ArrayList<InvokeNode>();
        }
        parentNode.childNodes.add(newNode);

        tree.curNode = newNode;

        //重复调用整理
        cleanRepeatNode(parentNode);
    }


    public static void cleanRepeatNode(InvokeNode parentNode) {
        if (parentNode == null) {
            return;
        }
        int len = parentNode.childNodes.size();
        if (len <= 1) {
            cleanRepeatNode(parentNode.parentNode);
            return;
        }
        InvokeNode a = parentNode.childNodes.get(len - 2);
        InvokeNode b = parentNode.childNodes.get(len - 1);
        if (a.equals(b)) {
            parentNode.childNodes.remove(len - 1);
            a.invokeCount++;
        }
        cleanRepeatNode(parentNode.parentNode);
    }


    public static void clear() {
        localTree.set(null);
    }

    public InvokeNode rootNode;

    public InvokeNode curNode;

    public static InvokeTree getCurrentTree() {
        return localTree.get();
    }

    public String toString() {
        if (rootNode == null) {
            rootNode = curNode;
        }
        if (rootNode == null) {
            return "Empty Tree";
        } else {
            StringBuilder sb = new StringBuilder();
            buildShow(rootNode, "", sb, true);
            return sb.toString();
        }
    }

    private void buildShow(InvokeNode node, String space, StringBuilder sb, boolean isParentLastNode) {

        if (node != null) {

            sb.append(space);
            if (node.parentNode != null) {
                sb.append("->");
            }
            sb.append(node.invokeMethod).append(node.invokeCount > 1 ? ("[repeat@" + node.invokeCount) + "]\n" : "\n");
            if (node.deep <= 8) {
                if (node.childNodes != null && node.childNodes.size() > 0) {

//                    for (int i = 0; i < node.childNodes.size(); i++) {
                        InvokeNode chNode = node.childNodes.get(node.childNodes.size()-1);
                        buildShow(chNode, space + ((node.parentNode != null
                                        && isParentLastNode) ? "|   " : "    "),
                                sb, /*(i != node.childNodes.size() - 1)*/true);

//                    }

                }
            }
        }
    }

    public static void main(String[] args) {
//        ClientConfig clientConfig = new ClientConfig();
//        clientConfig.setOpenInvokeTreeAnalyse(true);
//        BootConfig.clientConfig = clientConfig;
        InvokeTree.start("test");
        InvokeTree.enter("hello");
        InvokeTree.enter("invoke1");
        InvokeTree.enter("invokeSub1");
        InvokeTree.exit();
        InvokeTree.enter("invokeSub2");
        InvokeTree.exit();
        InvokeTree.enter("invokeSub2");
        InvokeTree.exit();
        InvokeTree.exit();

        InvokeTree.enter("invoke2");
        InvokeTree.enter("invoke21");
        InvokeTree.exit();
        InvokeTree.exit();

        InvokeTree.enter("invoke2");
        InvokeTree.enter("invoke21");
        InvokeTree.exit();
        InvokeTree.exit();

        InvokeTree.exit();
        InvokeTree.exit();
        System.out.println(InvokeTree.getCurrentTree().toString());
    }
}
