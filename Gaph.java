/**
 * name:Shaima Nimeri
 * date:12/01/2023
 * purpose of the program: implement the BFS and DFS algorithms for traversing a graph.
 * Graph.java is a driver class that represents a graph.
 * It contains code that reads a list of nodes and their adjacent nodes from a GraphInfo.txt file,
 *  and creates an ArrayList of Nodes that are in the graph.
 * */
/**
This class will be a driver class that demonstrates graph traversal algorithms
*/

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.File;
import java.io.IOException;
import java.util.Queue; //hint: might be useful for BFS - https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html
import java.util.LinkedList; //hint: might be useful for BFS - https://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html
import java.util.Stack; //hint: might be useful for DFS - https://docs.oracle.com/javase/8/docs/api/java/util/Stack.html

public class Graph
{
   public static void main(String[] args) throws IOException
   {
      //An ArrayList of Nodes, where each node contains an adjacency list of all nodes adjacent to it
      ArrayList<Node> graph = createGraph();
           
      //you can call displayGraph to have it print out the information in the graph
      //this is just provided to help you visualize each node in the graph, along with it's adjacent nodes
      displayGraph(graph);
      
      //call BFS and DFS here to have them print out their traversals
      breadthFirstSearch(graph);
      depthFirstSearch(graph);
      
   }
   
   /*
   Creates an ArrayList of Node objects that represent a graph
   
   Graph information is read from a GraphInfo.txt file contained in the same directory as this code
   Each line of the text file will contain information about a specific node and the nodes that are adjacent to it
   The nodes are delimited by a comma
   Example line of GraphInfo.txt:
   A,B,D
      A - the starting Node
      B D - nodes that are adjacent to A 
      This means that there is an edge from A to B and an edge from A to D
   This is an unweighted graph, so no need to worry about weights on the edges  
   
   @return graph The graph 
   */
   public static ArrayList<Node> createGraph() throws IOException
   {
      ArrayList<Node> graph = new ArrayList<Node>();
      
      File file = new File("GraphInfo.txt");
      Scanner inputFile = new Scanner(file);
      
      //read in the information from each line of the text file
      while(inputFile.hasNext())
      {
         String line = inputFile.nextLine();
         String[] tokens = line.split(",");//split the line of input on the delimiter (a comma)
         
         //make sure there is at least one node worth of information
         if(tokens.length > 0)
         {
            Node n = new Node(tokens[0]); //create a new Node object
            int nodeIndex = graph.indexOf(n);
            if(nodeIndex != -1)//node is already in the graph, retrieve a reference to it
               n = graph.get(nodeIndex);
            else//add it to the graph as a new node
               graph.add(n);  
                     
            //if there are any adjacent nodes, create them (or find them in the graph) and add them as an adjacent Node
            for(int i = 1; i < tokens.length; i++)
            {
               Node adj = new Node(tokens[i]);
               int adjNodeIndex = graph.indexOf(adj);
               if(adjNodeIndex != -1)//adjacent node is already in the graph, retrieve a reference to it
                  adj = graph.get(adjNodeIndex);
               else//add it to the graph as a new node
                  graph.add(adj);   
               n.addAdjacentNode(adj);
            }
         }
      }
      return graph;
   }
   
   /**
   Displays all Node information for each Node in the graph
   @param graph The graph
   */
   public static void displayGraph(ArrayList<Node> graph)
   {
      for(Node n : graph)
      {
         System.out.println(n);
      }
   }
   
   /**
   This method will perform a Breadth First Search traversal starting with the first Node contained in the graph
   Follow the algorithm described in 13.5.5 of your zyBook
   At the end of the method, you should display the contents of discoveredSet, which will be the traversal of the graph
   @param graph An ArrayList of Node objects representing a graph
   */
   
   public static void breadthFirstSearch(ArrayList<Node> graph)
   {
      //perform a breadth first traversal from the first Node in the graph (graph.get(0))
      //Follow the algorithm described in 13.5.5 of your zyBook
      //It lays out the entire algorithm in psuedocode - you just need to convert it into java code
     
	   //the start vertex
	   Node startV = graph.get(0);
	   //create a frontierQueue to enqueue the vertics starting with the firest vertics
	   Queue<Node> frontierQueue = new LinkedList<>();
	   //create a discovered set
       Set<Node> discoveredSet = new LinkedHashSet<>();
       //enque the start vertex
       frontierQueue.add(startV);
       //add the start vertex to the discovered set
       discoveredSet.add(startV);
       //while the frontierQueue is not empty
       while (!frontierQueue.isEmpty()) {
    	   //Dequeue the vertex from the queue
           Node currentV = frontierQueue.poll();
          // visit(currentV);
          // for each vertex adjV adjacent to currentV 
           for (Node adjV : currentV.getAdjacencyList()) {
               // if ( adjV is not in discoveredSet )
        	   if (!discoveredSet.contains(adjV)) {
        		   //Enqueue adjV in frontierQueue
                   frontierQueue.add(adjV);
                   //Add adjV to discoveredSet
                   discoveredSet.add(adjV);
               }
        	 
           }
       }
      System.out.println("BFS:");
      //print out the contents of discoveredSet - meaning the name of each node (don't use the toString() method since it includes the adjacency list)
      for (Node node : discoveredSet) {
          System.out.print(node.getName()+" ");
        }
    
      System.out.println();
   }
   
   /**
   This method will perform a Depth First Search traversal starting with the first Node contained in the graph
   Follow the algorithm described in 13.6.3 of your zyBook
   At the end of the method, you should display the contents of visitedSet, which will be the traversal of the graph
   @param graph An ArrayList of Node objects representing a graph
   */
   public static void depthFirstSearch(ArrayList<Node> graph)
   {
   
      //perform a depth first traversal from the first Node in the graph (graph.get(0))
      //Follow the algorithm described in 13.5.5 of your zyBook
      //It lays out the entire algorithm in psuedocode - you just need to convert it into java code
     
	  //the start vertex
	   Node startV = graph.get(0);
	   //create  a stack to push the starting vertex to
	   Stack<Node> stack = new Stack<>();
       Set<Node> visitedSet = new LinkedHashSet<>();
	   //Push startV to stack
       stack.push(startV);
       //while  stack is not empty 
       while (!stack.isEmpty()) {
    	   // pop the vertex from the top of the stack.
           Node currentV = stack.pop();
           // if currentV is not in visitedSet 
           if (!visitedSet.contains(currentV)) {
               // "Visit" currentV
        	   //Add currentV to visitedSet
               visitedSet.add(currentV);
               // for each vertex adjV adjacent to currentV
               for (Node adjV : currentV.getAdjacencyList()) {
                   // Push adjV to stack
            	   stack.push(adjV);
               }
           }
       }  
	   
      System.out.println("DFS:");
      //print out the contents of visitedSet - meaning the name of each node (don't use the toString() method since it includes the adjacency list)
      for (Node node : visitedSet) {
          System.out.print(node.getName()+" ");
        }
      System.out.println();
   }
}
