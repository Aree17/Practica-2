package unl.sing.base.controller.dataStruct.list;

import unl.sing.base.controller.dataStruct.stack.Stack;

public class LinkedList<E> {
    private Node<E> head;
    private Node<E> last;
    private Integer length;

    public Integer getLength() {
        return this.length;
    }

    public LinkedList() {
        head = null;
        last = null;
        length = 0;
    }

    public Boolean isEmpty() {
        return head == null || length == 0;
    }

    private Node<E> getNode(Integer pos) {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List empty");
        } else if (pos < 0 || pos >= length) {
            throw new ArrayIndexOutOfBoundsException("Index out range");
        } else if (pos == 0) {
            return head;
        } else if ((length.intValue() - 1) == pos.intValue()) {
            return last;
        } else {
            Node<E> search = head;
            Integer cont = 0;
            while (cont < pos) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    private E getDataFirst() {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List empty");
        } else {
            return head.getData();
        }
    }

    private E getDataLast() {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List empty");
        } else {
            return last.getData();
        }
    }

    public E get(Integer pos) {
        return getNode(pos).getData();
    }

    protected void addFirst(E data) {
        if (isEmpty()) {
            Node<E> aux = new Node<>(data);
            head = aux;
            last = aux;
        } else {
            Node<E> head_old = head;
            Node<E> aux = new Node<>(data, head_old);
            head = aux;
        }
        length++;
    }

    private void addLast(E data) {
        if (isEmpty()) {
            addFirst(data);
        } else {
            Node<E> aux = new Node<>(data);
            last.setNext(aux);
            last = aux;
            length++;
        }

    }

    public void add(E data, Integer pos) throws Exception {
        if (pos == 0) {
            addFirst(data);
        } else if (length.intValue() == pos.intValue()) {
            addLast(data);
        } else {
            Node<E> search_preview = getNode(pos - 1);
            Node<E> search = getNode(pos);
            Node<E> aux = new Node<>(data, search);
            search_preview.setNext(aux);
            length++;
        }
    }

    public void add(E data) {
        addLast(data);
    }

    public String print() {
        if (isEmpty())
            return "Esta vacia";
        else {
            StringBuilder resp = new StringBuilder();
            Node<E> help = head;
            while (help != null) {
                resp.append(help.getData()).append(" - ");
                help = help.getNext();
            }
            resp.append("\n");
            return resp.toString();
        }
    }

    public void update(E data, Integer pos) {
        getNode(pos).setData(data);
    }

    public void clear() {
        head = null;
        last = null;
        length = 0;
    }

    public E[] toArray() {
        Class clazz = null;
        E[] matriz = null;
        if (this.length > 0) {
            clazz = head.getData().getClass();
            matriz = (E[]) java.lang.reflect.Array.newInstance(clazz, this.length);
            Node<E> aux = head;
            for (int i = 0; i < length; i++) {
                matriz[i] = aux.getData();
                aux = aux.getNext();
            }
        }
        return matriz;
    }

    public LinkedList<E> toList(E[] matriz) {
        clear();
        for (int i = 0; i < matriz.length; i++) {
            this.add(matriz[i]);
        }
        return this;
    }

    protected E deleteFirst() throws Exception {
        if (isEmpty()) {
            throw new Exception("List empty");
        } else {
            E element = head.getData();
            Node<E> aux = head.getNext();
            head = aux;
            if (length.intValue() == 1)
                last = null;
            length--;
            return element;
        }
    }

    protected E deleteLast() throws Exception {
        if (isEmpty()) {
            throw new Exception("List empty");
        } else {
            E element = last.getData();
            Node<E> aux = getNode(length - 2);
            if (aux == null) {
                last = null;
                if (length == 2) {
                    last = head;
                } else {
                    head = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            length--;
            return element;
        }
    }

    public E delete(Integer pos) throws Exception {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("List empty");

        } else if (pos < 0 || pos >= length) {
            throw new ArrayIndexOutOfBoundsException("Index out range");
        } else if (pos == 0) {
            return deleteFirst();
        } else if ((length.intValue() - 1) == pos.intValue()) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(pos - 1);
            Node<E> actualy = getNode(pos);
            E element = preview.getData();
            Node<E> next = actualy.getNext();
            actualy = null;
            preview.setNext(next);
            length--;
            return element;
        }
    }

    public boolean contains(E value) {
        Node<E> actual = head;
        while (actual != null) {
            if (actual.getData().equals(value)) {
                return true;
            }
            actual = actual.getNext();
        }
        return false;
    }

    public void quickSort(int arr[], int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private int partition(int arr[], int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                int swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }

        int swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }

    public int sort(int arr[]) {
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {
                int temp = arr[i];
                int j;
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                arr[j] = temp;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>(5);

    }

}
