/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 *
 * Tai pirmosios kolekcijų duomenų struktūros klasės interfeisas,
 * apibrėžiantis operacijas, kurių pagalba atskiri objektai apjungiami
 * į vieną visumą - sąrašą.
 *
 * Užduotis: Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 ***************************************************************************** */
package edu.ktu.ds.lab1b.util;

public interface List<E> {

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return true, if operation is Ok
     */
    boolean add(E e);

    boolean add(int k, E e);

    /**
     * Returns the number of elements in this list.
     *
     * @param k index of the element to be removed
     * @return removed object e
     */
    E remove(int k);

    int size();

    boolean isEmpty();

    /**
     * Removes all of the elements from this list.
     */
    void clear();

    /**
     * Positional Access Operations. Returns the element at the specified
     * position in this list.
     *
     * @param k index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    E get(int k);

    E getNext();

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param k index of the element to replace
     * @param e element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    E set(int k, E e);
}
