/**
 * Created by macbook on 2/6/17.
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {

    private int n = 0;   // n is the size of queue.
    private Item[] items;

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        items = (Item[]) new Object[1];
        n = 0;
    }

    public static void main(String[] args)   // unit testing (optional)
    {
        RandomizedQueue<Integer> randomQueue = new RandomizedQueue<Integer>();

        randomQueue.enqueue(1);
        randomQueue.enqueue(2);
        randomQueue.enqueue(3);
        randomQueue.enqueue(4);
        randomQueue.enqueue(5);
        randomQueue.enqueue(6);
        randomQueue.enqueue(7);
        randomQueue.enqueue(8);
        randomQueue.enqueue(9);
        randomQueue.dequeue();
        randomQueue.dequeue();

        StdOut.println("Output: ");

        for (Integer x : randomQueue) {
            StdOut.print(x + " ");
        }
    }


    public boolean isEmpty()                 // is the queue empty?
    {
        return n == 0;
    }

    public int size()                        // return the number of items on the queue
    {
        return n;
    }

    private void resize(int x) {
        Item[] temp = (Item[]) new Object[x];
        for (int i = 0; i < size(); i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    public void enqueue(Item item)           // add the item
    {
        if (item == null) throw new NullPointerException();
        if (n == items.length) {
            resize(2 * n);
        }
        items[n] = item;
        n++;
    }

    public Item dequeue()                    // remove and return a random item
    {
        if (isEmpty()) throw new NoSuchElementException();
        int random = StdRandom.uniform(n);
        Item item = items[random];
        items[random] = items[n - 1];  // move the N-1 item to the deducted position
        n--;
        if (n > 0 && n == (items.length / 4)) {
            resize((items.length / 2));    // mistake: items.length is not n.
        }
        return item;
    }

    public Item sample()                     // return (but do not remove) a random item
    {
        if (isEmpty()) throw new NoSuchElementException();
        int random = StdRandom.uniform(n);
        Item item = items[random];
        return item;

    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new QueueIterator();

    }

    private class QueueIterator implements Iterator<Item> {
        private int index = 0;   // mistake: a parameter should be initialized.

        private Item[] newArray = (Item[]) new Object[n];

        // wrong:  private Item[] newArray = (Item[]) new Object[items.length];
        //  length is always larger than n because of the resize operation.


        // mistake:

        // private Item newArray[] = (Item[]) new Object[size];
        // style : Type[] para, instead of Type para[]



        private QueueIterator() {
            for (int i = 0; i < n; i++) {
                newArray[i] = items[i];
            }
            index = n;    // mistake: index = n-1
        }

        // @Override
        public boolean hasNext() {
            return index > 0;
        }   // mistake: newArray.length > 0. newArray.length will
        // never change, iteration will not terminate.


        // @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int random = StdRandom.uniform(index);
            //  wrong: int random = StdRandom.uniform(n);
            // range should be deducted after each iteration. index is the decreased number, not n.

            Item item = newArray[random];
            if (random != index - 1) {
                newArray[random] = newArray[index - 1];
                newArray[index -1] = null;

               // index--;
                // index is used as iteration mark, in each iteration, it should be deducted, so
                // it should be outside this loop.

            }
            index--;    // yes, move index-- here.


            return item;
        }

        //@Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

