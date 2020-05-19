/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 09 23
 *
 * Tai yra  interfeisas, kurį turi tenkinti KTU studentų kuriamos duomenų klasės
 *       Metodai užtikrina patogų duomenų suformavimą iš String eilučių
 ***************************************************************************** */
package edu.ktu.ds.lab1b.util;

public interface Parsable<T> extends Comparable<T> {
    void parse(String dataString);     // suformuoja objektą iš eilutės
}
