
package edu.senai.Miguel.simuladorrpg.services;

import edu.senai.Miguel.simuladorrpg.classes.Monstro;
import edu.senai.Miguel.simuladorrpg.model.MonstroEnum;
import java.util.ArrayList;
import java.util.List;

public class MonstroService {
        public static List<Monstro> ObterMonstros() {
        List<Monstro> monstros = new ArrayList<>();
        for (MonstroEnum monstro : MonstroEnum.values()) {
            Monstro m = new Monstro();
            m.setNome(monstro.getNome());
            m.setNivel(monstro.getNivel());
            monstros.add(m);
        }        
        return monstros;
    }
}
