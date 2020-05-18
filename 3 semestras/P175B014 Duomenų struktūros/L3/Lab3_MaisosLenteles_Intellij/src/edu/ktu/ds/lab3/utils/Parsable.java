package edu.ktu.ds.lab3.utils;

/**
 * @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra
 * <p>
 * Tai yra  interfeisas, kurį turi tenkinti KTU studentų kuriamos duomenų klasės
 * Metodai užtikrina patogų duomenų suformavimą iš String eilučių
 ******************************************************************************/
public interface Parsable<T> {

    /**
     * Suformuoja objektą iš teksto eilutės
     *
     * @param e
     */
    void parse(String e);
}
