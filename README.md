# CSCI 1102 Computer Science 2

### Fall 2018

------

## Lecture Notes

### Week 5: Aspects of Java; Priority Queues

#### Topics:

1. Aspects of Java
2. Order-sensitive Data Structures — Priority Queues
---

## 1. Aspects of Java

### A. Java's Type Hierarchy

Java was introduced in the early 1990s -- the heyday of object-oriented programming. In class-based OO languages, classes are usually organized in a tree structure and class variables and functions can be *inherited* from top to bottom in the tree. In Java, the class `Object` is at the root of the tree and every class inherits from that.  What is "inherited"? Well, since the class `Object` has `toString` and `equals` functions, *every* object has `toString` and `equals` functions. One can extend a class using the `extends` keyword:

```java
public class ColorPoint extends Point { ... }
```

The extending class is called a *subclass*, the ancestor classes are called *superclasses*. The designers of Java were attempting to help programmers avoid useless work by allowing them to reuse function definitions inherited from superclasses. In OO languages, a common idiom is to *override* a function inherited from a superclass. For example, the `toString` function inherited from class `Object` is pretty useless, so most class definitions override it with a more reasonable definition. See the examples in `src/Inherit.java` and `src/Override.java`.

When one class extends another, attributes are generally being added in the extending definition so there are *fewer* objects possessing the additional properties. 

```
        +----------------------------------------------+
        | Object                                       |
        | +--------------------------------+           |
        | | Point                          |           |
        | | +-----------------------+      |           |
        | | | ColorPoint            |      |           |
        | | +-----------------------+      |           |
        | +--------------------------------+           |
        +----------------------------------------------+
```

Class extension is sometimes called *implementation inheritance*. Implementation inheritance has a number of technical problems and the problems that it was intended to solve are better solved using generics. This is why people don't use it much anymore. We definitely stay away from it in CSCI1102.

#### Interface Inheritance

It's not only classes that are organized in a hierarchy, interfaces are too. 

```java
public interface A extends B { ... }
```

As with implementation inheritance, the tree structure matches up with set diagrams of the kind shown above. Since an extending interface impose more constraints than the extended interface, there are fewer objects satisfying the extending interface than the extended one. Interface inheritance does not have the problems associated with implementation inheritance.

### B. Exceptions

Most modern programming languages provide some mechanisms to deal with unusual or unexpected situations. These are usually in the form of exceptions and their handlers. Exceptions allow a programmer to alter the normal stack-like behavior of function calls and function returns. If `f` calls `g` and `g` calls `h`, `h` may encounter a problem that will cause it to resume control in `f` without ever returning to the calling point in `g`. See the examples in `src`.

**Checked and Unchecked Exception**

In Java, exceptions are integrated into the class hierarchy. A `RuntimeException` is an `Exception` which is a `Throwable`. 

```
                                +-------------+
                                |    Object   |
                                +-------------+
                                   /       \
                                ...  +-------------+  
                                     |  Throwable  |
                                     +-------------+
                                         /      \
                                  +--------+   +---------------+
                                  |  Error |   |   Exception   |
                                  +--------+   +---------------+
                                                  /         \
                                               ...    +------------------+
                                                      | RuntimeException |
                                                      +------------------+
```

RuntimeExceptions are "unchecked". This means that the Java compiler doesn't require you to making any assertions about them. All other Exceptions are "checked". For checked exceptions, the programmer has to take special action --- either handle the exception with an explicit `try-catch` form or announce that the exception isn't handled in the function prototype. See the examples in `src`.

### C. Collections & Iterators

The interface `java.util.Collection<T>` is a prominent feature of Java's `util` package. A `Collection<T>` is just that --- a collection of values of type `T`. Examples of collections include sets and queues. It turns out that one often needs to process each element of an exception in some way, i.e., to iterate through the items, visiting each one. So `java.util` has an interface called `Iterable<T>`, a super-interface of `Collection<T>`. This means that every collection has a function:

```java
Iterator<T> iterator();
```

For example, one might have

```java
ArrayList<Integer> a = new ArrayList<Integer>();
a.add(1);
a.add(2);
a.add(3);
```

Then, since `ArrayList<T>` is one of the known implementations of `Collection<T>`, one could have

```java
Iterator<Integer> intIter = a.iterator();
while (intIter.hasNext())
  doSomethingWith(intIter.next());
```

Or, as of a fairly recent version of Java, one could use the shorter for-loop notation:

```java
for (Integer i : a)
  doSomethingWith(i);
```

This handy idiom is now quite common.

### D. Enums

There are many occasions when you'll want to create a finite type, i.e., a type with a fixed number of values.

```java
public enum Color { Red, Blue, Green }
```

Given that definition of the enumeration type `Color`,  one can use `Color` as a type as in:

```java
Color color = Green;
```



---

## 2. Order-sensitive Data Structures — Priority Queues

A queue is a FIFO structure, the first item inserted is the first one removed. It's sometimes desirable to be able to maintain a queue in which insertion and removal are sensitive to a *priority*. Of course, this will require is to be able to compare the priorities of items in the queue. We'll come back to the comparing issue later.

#### Example:

A Minimum Priority Queue, or minPQ, containing letters with a priority of alphabetical ordering.

```
Operation        PQ (front on left)
                 empty
 insert(F)       F
 insert(L)       F L
 insert(C)       C F L
 remove()        F L              smallest C is taken out
 insert(G)       F G L
```

An API for a Max Priority Queue:

```java
public interface MaxPQ<Key extends Comparable<Key>> {
  void insert(Key v);
  Key max();
  Key delMax();
  boolean isEmpty();
  int size();
}
```

NOTE: the notation for the type parameter <Key extends Comparable<Key>> means that ...

---

Thinking about possible implementations:

+ a sequential resizing array implementation would *seem* a poor choice because the insert operation would seem to require linear complexity and would require shifting items around in the array,

+ a linked implementation could work but the complexity of the insert operation would still be linear.


It turns out that a *binary heap* is an ideal structure for implementing a PQ. In particular, a binary heap can support logarithmic insert and remove in constant space.

A binary heap is a binary tree with certain structural and relational properties. Since these properties must be preserved in the management of the data structure, they are called *invariants*. 

---

## Trees

Let $\mathrm{Sym}$ be a set of indexed symbols $A_k$ where $A$ is a symbol and $k$ is a non-negative integer index or *degree*. Then 
$$
\mathrm{Trees}(\mathrm{Sym}) = \{ A_k(t_1, \ldots, t_k) \mid t_i\in \mathrm{Trees}(\mathrm{Sym} )\}
$$

##### Examples

Let $\mathrm{Syms}_1 = \{ A_2, B_0, C_1, D_3 \}$. Then $\mathrm{Trees}(\mathrm{Sym}_1)$ includes $t_0 = B_0()$, $t_1 = A_2(B_0(), B_0())$ and $t_2 = C_1(B_0())$.

**Convention** 

We'll usually omit the degree. And when the degree is 0, we'll omit the the empty parentheses as well. So $t_0$, $t_1$ and $t_2$ above will usually be written as $B$, $A(B, B)$ and $C(B)$.

Note the if $\mathrm{Sym}$ has no symbols with degree 0 then $\mathrm{Trees}(\mathrm{Sym})$ is an empty set. Symbols with degree 0 are the *base case* of this recursive definition.

**Tree Diagrams**

The choice of the word "tree" is easier to understand when these objects are diagrammed.

```
 t0 =  B            t1 =  A          t2 =  C          t3 =  A            t4 =  A    
                         / \               |               / \                / \
                        B   B              B              A   B              B   A
                                                         / \                    / \
                                                        B   B                  B   B
 t5 =  D            t6 =  A
     / | \              /   \
    B  B  B            A     A
                      / \   / \
                     B   B B   B
```

##### Terminology

Let $t = A(t_1, \ldots, t_k)$ be a tree. Then $A$ is the *root* of $t$, the trees $t_1, \ldots, t_k$ are the *children* of $A$ and $A$ is the *parent*. The symbols in $t$ are often called *nodes*. A tree with degree 0 (i.e., a node with no children such as $B$ above) is called a *leaf*. Non-leaf nodes are called *interior nodes*.

##### Depth & Height

The *depth* of a node in a tree is the number of hops to the root. Nodes with the same depth are said to be on the same *level*. The *height* of a tree is the maximum depth of any node in the tree.

#### Types of Trees

+ A tree is a *binary tree* if all nodes in the tree have degree 0, 1 or 2. All trees above except t5 are binary trees. 

+ A binary tree is a *full binary tree* if all nodes have degree 0 or 2. Only t2 and t5 are not full binary trees.

+ A full binary tree is a *perfect binary tree* if all leaves are on the same level. Only t0, t1 and t6 are perfect binary trees. A perfect binary tree of height $k$ has $2^k$ leaves and $2^k - 1$ interior nodes. Thus it has $2^{k + 1} - 1$ nodes altogether. Put another way, a perfect binary tree with $n$ leaves is of height $\mathrm{log}_2 n $. **It should be noted that even for very large $n$, $\mathrm{log}_2 n$ is quite small.**

  |        $n$         | $10^3$ | $10^6$ | $10^9$ | $10^{12}$ |
  | :----------------: | :----: | :----: | :----: | :-------: |
  | $\mathrm{log}_2 n$ |  ~10   |  ~20   |  ~30   |    ~40    |

+ A binary tree is a *complete binary tree* if all levels are full except possibly the last one and if the last level isn't full, all leaves are to the left. Only t0, t1, t2, t3 and t6 are complete binary trees. Note that every perfect binary tree is complete but not vice-versa and that there are incomplete full binary trees (e.g., t4) and complete binary trees that aren't full (e.g., t2).

#### Representing Complete Binary Trees

Complete binary trees are of interest because they can be efficiently represented in sequential storage by reading each level from left to right. For example, we can represent the complete binary tree

```
             T
           /   \
         P       R
       /   \    / \
      N     H  O   A
     / \   /
    E   I G
```

sequentially as:

```
0 1 2 3 4 5 6 7 8 9 10
- T P R N H O A E I  G
```

If i is the array index of a node, then

```
leftChild(i) = i * 2
rightChild(i) = i * 2 + 1
parent(i) = int(i / 2)
```

So the structural invariant of a binary heap is that it must be a complete binary tree. We'll add a relational invariant next time.
