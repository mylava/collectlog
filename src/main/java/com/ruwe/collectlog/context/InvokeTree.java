package com.ruwe.collectlog.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lipengfei on 2017/6/1.
 */
public class InvokeTree implements Serializable{
    //方法起始节点
    private InvokeNode rootNode;
    //当前节点
    private InvokeNode curNode;

    private int deep;

    public InvokeNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(InvokeNode rootNode) {
        this.rootNode = rootNode;
    }

    public InvokeNode getCurNode() {
        return curNode;
    }

    public void setCurNode(InvokeNode curNode) {
        this.curNode = curNode;
    }

    public static class InvokeNode {
        public InvokeNode(int deep) {
            this.deep = deep;
            //调用次数
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

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("InvokeNode{");
            sb.append("invokeMethod='").append(invokeMethod).append('\'');
            sb.append(", invokeCount=").append(invokeCount);
            sb.append('}');
            return sb.toString();
        }
    }

    public InvokeTree() {
    }

    /**
     * 调用方法开始
     * @param invokeMethod
     */
    public void start(String invokeMethod) {
        InvokeNode rootNode = new InvokeTree.InvokeNode(0);
        rootNode.invokeMethod = invokeMethod;
        this.rootNode = rootNode;
        this.curNode = rootNode;
        this.deep = 0;
    }

    /**
     * 进入方法
     * @param invokeMethod
     */
    public void enter(String invokeMethod) {
        InvokeNode parentNode = this.curNode;
        InvokeNode newNode = new InvokeNode(parentNode.deep + 1);
        newNode.invokeMethod = invokeMethod;
        newNode.parentNode = parentNode;
        if (parentNode.childNodes == null) {
            parentNode.childNodes = new ArrayList();
        }
        parentNode.childNodes.add(newNode);
//        System.out.println("\nparentNode="+parentNode+"********* childnodes="+parentNode.childNodes);
        this.curNode = newNode;
        this.deep++;
        //重复调用整理
        cleanRepeatNode(parentNode);
    }

    /**
     * 删除重复调用的方法
     * @param parentNode
     */
    public void cleanRepeatNode(InvokeNode parentNode) {
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
            //调用次数加一
            a.invokeCount++;
        }
        cleanRepeatNode(parentNode.parentNode);
    }

    public void exit() {
        this.curNode = this.curNode.parentNode;
        this.deep--;
//        this.curNode.childNodes = null;
    }

/*    public void clear() {
        local.set(null);
    }

    public InvokeTree getCurrentTree() {
        return local.get();
    }*/

    public String toString() {
        if (rootNode == null) {
            rootNode = curNode;
        }
        if (rootNode == null) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            buildShow(rootNode, sb);
            return sb.toString();
        }
    }

    private void buildShow(InvokeNode node, StringBuilder sb) {
        if (node != null) {
            if (node.parentNode != null) {
                sb.append("->");
            }
            if (this.curNode != null) {
                sb.append(node.invokeMethod).append(node.invokeCount > 1 ? ("[repeat@" + node.invokeCount) + "]" : "");
            }
            //控制调用深度只打印8以内的
            if (this.deep>node.deep && node.deep <= 8) {
                if (node.childNodes != null && node.childNodes.size() > 0) {
                    InvokeNode chNode = node.childNodes.get(node.childNodes.size()-1);
                    buildShow(chNode, sb);

                }
            }
        }
    }

    public static void main(String[] args) {
//        ClientConfig clientConfig = new ClientConfig();
//        clientConfig.setOpenInvokeTreeAnalyse(true);
//        BootConfig.clientConfig = clientConfig;
        InvokeTree invokeTree = new InvokeTree();
        invokeTree.start("controller.test");
        System.out.println("进1->"+invokeTree.toString()+"==========="+invokeTree.toString().split("->").length);

        invokeTree.enter("service.hello");
        System.out.println("进2->"+invokeTree.toString()+"==========="+invokeTree.toString().split("->").length);
        invokeTree.enter("invoke1");
        System.out.println("进3->"+invokeTree.toString()+"==========="+invokeTree.toString().split("->").length);
        invokeTree.enter("invokeSub1");
        System.out.println("进4->"+invokeTree.toString()+"==========="+invokeTree.toString().split("->").length);
        invokeTree.exit();
        System.out.println("退1->"+invokeTree.toString()+"\t+-----"+invokeTree.toString().split("->").length);


        invokeTree.enter("invokeSub2");
        System.out.println("进5->"+invokeTree.toString()+"==========="+invokeTree.toString().split("->").length);
        invokeTree.exit();
        System.out.println("退2->"+invokeTree.toString()+"\t+-----"+invokeTree.toString().split("->").length);
        invokeTree.enter("invokeSub2");
        System.out.println("进7->"+invokeTree.toString()+"==========="+invokeTree.toString().split("->").length);
        invokeTree.exit();
        System.out.println("退3->"+invokeTree.toString()+"\t+-----"+invokeTree.toString().split("->").length);
        invokeTree.exit();
        System.out.println("退4->"+invokeTree.toString()+"\t+-----"+invokeTree.toString().split("->").length);

        invokeTree.enter("invoke2");
        System.out.println("进8->"+invokeTree.toString()+"==========="+invokeTree.toString().split("->").length);
        invokeTree.enter("invoke21");
        System.out.println("进9->"+invokeTree.toString()+"==========="+invokeTree.toString().split("->").length);
        invokeTree.exit();
        System.out.println("退5->"+invokeTree.toString()+"\t+-----"+invokeTree.toString().split("->").length);
        invokeTree.exit();
        System.out.println("退6->"+invokeTree.toString()+"\t+-----"+invokeTree.toString().split("->").length);

        invokeTree.enter("invoke2");
        System.out.println("进10->"+invokeTree.toString()+"==========="+invokeTree.toString().split("->").length);
        invokeTree.enter("invoke21");
        System.out.println("进11->"+invokeTree.toString()+"==========="+invokeTree.toString().split("->").length);
        invokeTree.exit();
        System.out.println("退7->"+invokeTree.toString()+"\t+-----"+invokeTree.toString().split("->").length);
        invokeTree.exit();
        System.out.println("退8->"+invokeTree.toString()+"\t+-----"+invokeTree.toString().split("->").length);

        invokeTree.exit();
        System.out.println("退9->"+invokeTree.toString()+"\t+-----"+invokeTree.toString().split("->").length);
        invokeTree.exit();
        System.out.println("退10->"+invokeTree.toString()+"\t+-----"+invokeTree.toString().split("->").length);

    }
}
