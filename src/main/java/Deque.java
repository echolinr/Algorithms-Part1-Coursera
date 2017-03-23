import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node front;  // must be private
    private Node end;
    private int n;

    /**
     * Construct an empty deque
     */
    public Deque() {
        n = 0;
        front = null;
        end = null;
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        Deque<String> deque = new Deque<String>();
        //      deque.addFirst("the first");
        //    deque.addFirst("the first");
        deque.addLast("the last1");
        deque.addLast("the last2");
        deque.removeFirst();
        deque.addFirst("the first1");
        deque.removeLast();
        deque.removeFirst();
        Iterator<String> it1 = deque.iterator();
        for (int i = 0; i < deque.size(); i++) {
            String str = it1.next();
            System.out.println(str);
        }
        //       System.out.println(it1.next());

//        deque.removeFirst();
//        deque.removeFirst();
//        deque.removeLast();
//        deque.removeLast();
    }

    public boolean isEmpty()                 // is the deque empty?
    {
        if (n == 0) {
            return true;
        }
        return false;
    }

    public int size()                        // return the number of items on the deque
    {
        return n;
    }

    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) throw new NullPointerException();

        Node oldFront = front;
        front = new Node();

        front.pre = null;
        front.item = item;
        front.next = oldFront;     // run test on diff order

        if (isEmpty()) {
            end = front;
        } else {
            oldFront.pre = front;
        }
        n++;
    }

    public void addLast(Item item)           // add the item to the end
    {
        if (item == null) throw new NullPointerException();

        Node oldEnd = end;
        end = new Node();
        end.item = item;

        if (isEmpty()) {
            end.pre = null;
            end.next = null;
            front = end;
        } else {
            end.next = null;
            oldEnd.next = end;
            end.pre = oldEnd;
        }
        n++;
    }

    public Item removeFirst()                // remove and return the item from the front
    {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = front.item;

        if (n == 1) {
            front = null;
            end = null;
        } else {
          //  front.pre = null;      // mistake: assign front first, then assign .pre.
            front = front.next;
            front.pre = null;
        }
        n--;
        return item;
    }

    public Item removeLast()                 // remove and return the item from the end
    {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = end.item;
        if (n == 1) {
            front = null;
            end = null;
        } else {
            end = end.pre;
            end.next = null;

        }
        n--;
        return item;
    }

    public Iterator<Item> iterator() {
        Iterator<Item> it = new Iterator<Item>() {
            private Node current = front;

            public boolean hasNext() {
                return current != null;
            }

            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Item item = current.item; // mistake: redundant cast: Item item = (Item) current.item;
                current = current.next;
                return item;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    private class Node {

        private Node pre;   // private class's parameter, delcare
        private Node next;
        private Item item;

    }
}
