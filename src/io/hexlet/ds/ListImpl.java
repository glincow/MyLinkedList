package io.hexlet.ds;
import java.util.*;

public class ListImpl<T> implements List<T>{

    private Item<T> first = null;

    private Item<T> last = null;

    private int size;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
        // BEGIN (write your solution here)
        return (indexOf(o) != -1);
        // END
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public Object[] toArray() {
        // BEGIN (write your solution here)
        Object[] array = new Object[this.size];
        ListIterator<T> it = new ElementsIterator(0);
        for (int i = 0; i < size; i++){
            array[i] = it.next();
        }
        return array;
        // END
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        // BEGIN (write your solution here)
        if (a.length < size)
            return (T1[]) Arrays.copyOf(this.toArray(), size, a.getClass());
        System.arraycopy((T1[])this.toArray(), 0, a, 0, size);

        if (a.length > size)
            a[size] = null;

        return a;
        // END
    }

    @Override
    public boolean add(final T t) {
        // BEGIN (write your solution here
        Item<T> newItem = new Item<>(t, last, null);
        if (last == null)
            first = newItem;
        else
            last.next = newItem;
        last = newItem;
        size++;
        return true;


        // END
    }

    @Override
    public boolean remove(final Object o) {
        // BEGIN (write your solution here)
        if (o == null) {
            for (Item<T> item = first; item != null; item = item.next){
                if (item.getElement() == null) {
                    item.prev.next = item.next;
                    item.next.prev = item.prev;
                    size--;
                }

                return true;
            }
        } else {
            for (Item<T> item = first; item != null; item = item.next) {
                if (item.getElement().equals((T) o)) {
                    if (item == first) {
                        item.next.prev = null;
                        first = item.next;
                        size--;
                        return true;
                    }
                    if (item == last) {
                        item.prev.next = null;
                        last = item.prev;
                        size--;
                        return true;
                    }
                    item.next.prev = item.prev;
                    item.prev.next = item.next;
                    size--;
                    return true;

                }


            }

        }
        return false;
        // END
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        for (final Object item : this) {
            if (!c.contains(item)) this.remove(item);
        }
        return true;
    }

    @Override
    public void clear() {
        // BEGIN (write your solution here)
        first = last = null;
        size = 0;
        // END
    }

    @Override
    public T remove(final int index) {
        // BEGIN (write your solution here)
        if (index == 0) {
            Item<T> item = first;
            if (first == last) {
                this.clear();
                return item.element;
            }
            item.next.prev = null;
            first = item.next;
            size--;
            return item.element;
        }
        if (index == size - 1) {
            Item<T> item = last;
            item.prev.next = null;
            last = item.prev;
            size--;
            return item.element;
        }
        Item<T> item = first;
        for (int i = 0; i < index; i++){
            item = item.next;
        }
        item.next.prev = item.prev;
        item.prev.next = item.next;
        size--;
        return item.element;
        // END
    }

    // BEGIN (write your solution here)

    // END
    @Override
    public List<T> subList(final int start, final int end) {
        return null;
    }

    @Override
    public ListIterator listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public ListIterator listIterator(final int index) {
        return new ElementsIterator(index);
    }

    @Override
    public int lastIndexOf(final Object target) {
        ListIterator<T> it = new ElementsIterator(this.size());
        while (it.hasPrevious()){
            if (it.previous().equals(target)) return it.previousIndex() + 1;
        }
        return -1;
    }

    @Override
    public int indexOf(final Object target) {
        // BEGIN (write your solution here)
        ListIterator<T> it = new ElementsIterator(0);
        while (it.hasNext()){
            if (it.next().equals(((T)target))) return it.previousIndex();
        }
        return -1;
        // END
    }

    @Override
    public void add(final int index, final T element) {
    	/*Item<T> item = first;
	    for (int i = 0; i < index; i++){
	        item = item.next;

	    }
	    //Item<T> =
	    item.prev.next = item.next;
	    item.next.prev = item.prev;*/
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final int index, final Collection elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(final int index, final T element) {
        // BEGIN (write your solution here)
        Item<T> item = first;
        for (int i = 0; i < index; i++)
            item = item.next;
        T value = item.element;
        item.element = element;
        return value;
        // END
    }

    @Override
    public T get(final int index) {
        // BEGIN (write your solution here)
        Item<T> item = first;
        for (int i = 0; i < index; i++)
            item = item.next;
        return item.element;
        // END
    }

    // BEGIN (write your solution here)

    // END

    private class ElementsIterator implements ListIterator<T> {

        private Item<T> current;

        private Item<T> last;

        private int index;

        private int lastChanged = -1;

        public ElementsIterator() {
            this(0);
        }

        public ElementsIterator(final int index) {
            // BEGIN (write your solution here)
            current = ListImpl.this.first;
            for (int i = 0; i < index; i++){
                current = ListImpl.this.first.getNext();
            }
            last = ListImpl.this.last;
            this.index = index;
            // END
        }

        @Override
        public boolean hasNext() {
            return (index < size);
        }

        @Override
        public T next() {
            // BEGIN (write your solution here)
            if (!hasNext())
                throw new NoSuchElementException();
            lastChanged = index;
            T a = current.getElement();
            if (current == last){
                index++;
                return a;
            }

            current = current.next;
            index++;
            return a;
            // END
        }

        @Override
        public void add(final T element) {
            ListImpl.this.add(element);
        }

        @Override
        public void set(final T element) {
            // BEGIN (write your solution here)
            if (lastChanged == -1) throw new IllegalStateException();
            if (!(ListImpl.this.get(lastChanged).getClass().equals(element.getClass()))) throw new ClassCastException();
            ListImpl.this.set(lastChanged, element);
            // END
        }

        @Override
        public int previousIndex(){
            // BEGIN (write your solution here)
            return index - 1;
            // END
        }

        @Override
        public int nextIndex() {
            // BEGIN (write your solution here)
            return index + 1;
            // END
        }

        @Override
        public boolean hasPrevious() {
            // BEGIN (write your solution here)
            return (index > 0);
            // END
        }

        @Override
        public T previous() {
            // BEGIN (write your solution here)
            if (!hasPrevious())
                throw new NoSuchElementException();
            //lastChanged = LinkedList.this.indexOf(current);
            //Item<T> item = current.getPrev();
            //return item.getElement();
            if (current == last){
                T a = current.getElement();
                index--;
                return a;
            }
            lastChanged = index;
            T a = current.prev.getElement();
            current = current.prev;
            index--;
            return a;
            // END
        }

        @Override
        public void remove() {
            // BEGIN (write your solution here)
            if (lastChanged == -1)
                throw new IllegalStateException();
            ListImpl.this.remove(lastChanged);
            index--;
            lastChanged = -1;
            // END
        }

    }

    private static class Item<T> {

        private T element;

        private Item<T> next;

        private Item<T> prev;

        public Item(final T element, final Item<T> prev, final Item<T> next) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }

        public T getElement() {
            return element;
        }

        public Item<T> getNext() {
            return next;
        }

        public Item<T> getPrev() {
            return prev;
        }

    }
}
