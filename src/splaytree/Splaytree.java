
package splaytree;

public class Splaytree 
{
    
    class Node
    {   
        int key;
        Node left;
        Node right;
        Node parent;

        //constructor
        public Node(int key) 
        {
            this.key = key;
        }
        
        boolean isLeaf()
        {
            return this.left==null && this.right==null;
        }
        
        boolean isEither()
        {
            return (this.left!=null && this.right==null) || (this.left==null && this.right!=null);
        }
        
        boolean isFull()
        {
            return this.left!=null && this.right!=null;
        }  
       
        public String toString() 
        {
            return "Node{" + "key=" + key + '}';
        }
    }
    
    class Queue
    {
        class nodeQ
        {
            Node data;
            nodeQ next;
            
            //constructor
            public nodeQ(Node data) 
            {
                this.data = data;
            }
          
            public String toString() 
            {
                return data.toString();
            }    
        }
        
        nodeQ head;
        nodeQ tail;
        int size = 0;
      
        public nodeQ dequeue() 
        {
            if(size == 0) //checks if empty
            {
                System.out.println("Queue is empty");
                return null;
            }
            nodeQ temp = head;
            head = head.next;
            size--;
            return temp;
        }

        public void enqueue(nodeQ n) 
        {
            if (size == 0)
            {
                head = n;
                tail = n;
                size++;
                return;
            }
            tail.next = n;
            tail = n;
            size++;
        }
        
        public void enqueue(Node data) 
        {
            nodeQ n = new nodeQ(data);
            if (size == 0)
            {
                head = n;
                tail = n;
                size++;
                return;
            }
            tail.next  = n;
            tail = n;
            size++;
        }
        
        //checks if tree is empty
        boolean isEmpty()
        {
            return size == 0;
        }
        
        public String toString() 
        {
            String st = "Queue{" + "\t";
            nodeQ current = head;
            
            for(int i=0;i<size;i++) 
            {
                st+= current.data.toString() + "\t";
                current = current.next;
            }
            st += " }";
        return st;
        }
    }
    
    public Splaytree(int data) 
    {
        root = new Node(data);
    }
    
    Node root;
    int count = 0;
    
    //to check if queue/tree is empty
    boolean isEmpty()
    {
        return root==null;
    }
    
    //clear node
    void clear()
    {
        root = null;
    }
    
    //for insertion of elements
    //simple binary tree insertion
    //element larger than root element is inserted at right
    //element smaller than root element is inserted at left
    void insert(int x)
    {
        Node temp = root,par = null;
        
        while(temp!=null)
        {
            par = temp;
            if(x>temp.key)temp = temp.right;
            else temp = temp.left;
        }
        temp = new Node(x);
        temp.parent = par;
        if(par == null) root = temp; 
        else if(x>par.key) par.right = temp;
        else par.left = temp;
        splay(temp);
        count++;
    }
    
    //splay rotations//
    
    //simple left rotation
    Node zigLeft(Node pivot)
    {
        Node parent = pivot.parent;
        pivot.parent = null;
        parent.right = null;
    
        if(pivot.left!=null)
        {
            parent.right = pivot.left;
            pivot.left.parent = parent;
        }
        pivot.left = parent;
        parent.parent = pivot;
        return pivot;
    }
    
    //simple right rotation
    Node zigRight(Node pivot)
    {
        Node parent = pivot.parent;
        pivot.parent = null;
        parent.left = null;
        
        if(pivot.right!=null)
        {
            parent.left = pivot.right;
            pivot.right.parent = parent;            
        }
        pivot.right = parent;
        parent.parent = pivot;
        return pivot;
    }
    
    //left left rotation
    Node zigZigLeft(Node pivot)
    {
        Node parent = pivot.parent;
        pivot.parent = null;
        parent.right = null;
        Node grandPar = parent.parent;
        parent.parent = null;
        grandPar.right = null;
        
        if(parent.left!=null)
        {
            grandPar.right = parent.left;
            parent.left.parent = grandPar;
        }
        
        if(pivot.left!=null)
        {
            parent.right = pivot.left;
            pivot.left.parent = parent;
        }
        
        parent.left = grandPar;
        grandPar.parent = parent;
        pivot.left = parent;
        parent.parent = pivot;
        return pivot;
    }
    
    //right right rotation
    Node zigZigRight(Node pivot)
    {
        Node parent = pivot.parent;
        pivot.parent = null;
        parent.left = null;
        Node grandPar = parent.parent;
        parent.parent = null;
        grandPar.left = null;
    
        if(parent.right!=null)
        {
            grandPar.left = parent.right;
            parent.right.parent = grandPar;
        }
        
        if(pivot.right!=null)
        {
            parent.left = pivot.right;
            pivot.right.parent = parent;
        }
        parent.right = grandPar;
        grandPar.parent = parent;
        pivot.right = parent;
        parent.parent = pivot;
        return pivot;
    }
    
    //left right rotation
    Node zigZagLeft(Node pivot)
    {
        Node parent = pivot.parent;
        pivot.parent = null;
        parent.left = null;
        Node grandPar = parent.parent;
        parent.parent = null;
        grandPar.right = null;
    
        if(pivot.left!=null)
        {
            grandPar.right = pivot.left;
            pivot.left.parent = grandPar;
        }
        
        if(pivot.right!=null)
        {
            parent.left = pivot.right;
            pivot.right.parent = parent;
        }
        pivot.left = grandPar;
        grandPar.parent = pivot;
        pivot.right = parent;
        parent.parent = pivot;
        return pivot;
    }
    
    //right left rotation
    Node zigZagRight(Node pivot)
    {
        Node parent = pivot.parent;
        pivot.parent = null;
        parent.right = null;
        Node grandPar = parent.parent;
        parent.parent = null;
        grandPar.left = null;
    
        if(pivot.right!=null)
        {
            grandPar.left = pivot.right;
            pivot.right.parent = grandPar;
        }
        if(pivot.left!=null)
        {
            parent.right = pivot.left;
            pivot.left.parent = parent;
        }
        pivot.right = grandPar;
        grandPar.parent = pivot;
        pivot.left = parent;
        parent.parent = pivot;
        return pivot;
    }
    
    void splay(Node current)
    {
        if(current == null || current.parent == null) return;
        Node parent = current.parent;
        Node grandParent = parent.parent;
        boolean isLeft;
    
        if(grandParent == null) 
        {
            isLeft = (parent.left == current);
            if(isLeft) 
            {
                System.out.println("Zig Right");
                root = zigRight(current);
            } 
            else 
            {
                System.out.println("Zig Left");
                root = zigLeft(current);
            }
        }
        else if ( grandParent.left==parent && parent.left == current || grandParent.right == parent && parent.right == current)
        {
            isLeft = (parent.parent.left == current.parent);
            
            if(isLeft) 
            {
                System.out.println("Zig Zig Right");
                Node temp = grandParent.parent;
                boolean t_left = false;
                if(temp != null) t_left = temp.left == grandParent;
                current = zigZigRight(current);
                
                if(temp != null)
                {
                    if(t_left) temp.left = current;
                    else temp.right = current;
                    current.parent = temp;
                }
                else root = current;
            }
            else 
            {
                System.out.println("Zig Zig Left");
                Node temp = grandParent.parent;
                boolean t_left = false;
                if(temp != null) t_left = temp.left == grandParent;
                current = zigZigLeft(current);
                if(temp != null )
                {
                    if(t_left) temp.left = current;
                    else temp.right = current;
                    current.parent = temp;
                }
                else root = current;
            }
        }
        else if ( grandParent.left == parent && parent.right == current || grandParent.right == parent && parent.left == current)
        {
            isLeft = (parent.parent.left == current.parent);
            if(isLeft) 
            {
                System.out.println("Zig Zag Right");
                Node temp = grandParent.parent;
                boolean t_left = false;
                if(temp != null) t_left = temp.left == grandParent;
                current = zigZagRight(current);
                
                if(temp!=null)
                {
                    if(t_left)temp.left = current;
                    else temp.right = current;
                    current.parent = temp;
                }
                else root = current;    
            }
            else 
            {
                System.out.println("Zig Zag Left");
                Node temp = grandParent.parent;                
                boolean t_left = false;
                if(temp != null) t_left = temp.left == grandParent;
                current = zigZagLeft(current);
                
                if(temp!=null)
                {
                    if(t_left)temp.left = current;
                    else temp.right = current;
                    current.parent = temp;
                }
                else root = current;
            }
        }
        
        BFS();
        splay(current);
    }
    
    void BFS() 
    {
        if (root == null) 
        {
            System.out.println("EMPTY TREE");
            return;
        }
        Queue queue = new Queue();
        queue.enqueue(root);
        String s = "";
    
        while(!queue.isEmpty())
        {
            Node u = queue.dequeue().data;
            s += u.toString() + " ";
            if (u.left != null ) queue.enqueue(u.left);
            if (u.right != null ) queue.enqueue(u.right);
        }
        System.out.println(s);
    }
    
    void BFS(Node root) 
    {
        if (root == null) 
        {
            System.out.println("EMPTY TREE");
            return;
        }
        Queue queue = new Queue();
        queue.enqueue(root);
        String s = "";
        while(!queue.isEmpty())
        {
            Node u = queue.dequeue().data;
            s += u.toString() + " ";
            if (u.left != null ) queue.enqueue(u.left);
            if (u.right != null ) queue.enqueue(u.right);
        }
        System.out.println(s);
    }    
    
    //look for a node
    Node findNode(int x)
    {
        System.out.println("\nSearching " + x);
        Node temp = root;
       
        while (temp != null) 
        {   
            if (x > temp.key)
                temp = temp.right;
            else if (x < temp.key)
                temp = temp.left;
            else 
            {
                splay(temp);
                return root;
            }
        }
        return null;
    }
    
    //delete node
    Node delete(int x)
    {
        System.out.println("\nDeleting " + x);
        
        if(root == null)
        {
            System.out.println("Tree is EMPTY.");
            return null;
        }
        if(findNode(x)==null) 
        {
            System.out.println(x + " is not present in the tree.");
            return null;
        }
        Node temp = root;
        count--;
        
        if(root.isLeaf())
        {
            root = null;
            BFS();
            return temp;
        }
        if(root.isEither())
        {
            if(root.left!=null)
            {
                root.left.parent = null;
                root = root.left;
                BFS();
                return temp;
            }
            root.right.parent = null;
            root = root.right;
            BFS();
            return temp;
        }
        Node leftTree = root.left;
        Node rightTree = root.right;
        
        if(leftTree.right == null)
        {
            leftTree.parent = null;
            leftTree.right = rightTree;
            rightTree.parent = leftTree;
            root = leftTree;
            BFS();
            return temp;
        }
        rightTree.parent = null;
        leftTree.parent = null;
        Node t = predecessor(leftTree);
        splay(t);
        root.right = rightTree;
        rightTree.parent = root;
        BFS();
        return temp;
    }
    
    //next node
    private Node successor(Node pivot)
    {
        while(pivot.left != null) pivot = pivot.left;
        return pivot;
    }
    
    //previous node
    private Node predecessor(Node pivot)
    {
        while(pivot.right != null) pivot = pivot.right;
        return pivot;
    }
    
    public static void main(String[] args) 
    {
        Splaytree s = new Splaytree(9); //root node is 9.
//        elemwnts inserted after root node

        s.insert(30);
        s.insert(22);
        s.insert(26);
        s.insert(41);
        s.insert(47);
        s.insert(33);
        s.insert(50);
        s.insert(36);
        s.insert(25); //insert this element
        s.findNode(50); //search this element 
        s.delete(36);   //delete this element
    }
}
 
