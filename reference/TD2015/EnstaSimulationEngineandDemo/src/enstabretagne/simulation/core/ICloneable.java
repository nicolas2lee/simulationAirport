/**
* Classe ICloneable.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.simulation.core;
// R�sum�:
//     Prend en charge le clonage, qui cr�e une nouvelle instance d'une classe avec
//     la m�me valeur qu'une instance existante.
public interface ICloneable extends Cloneable
{
    // R�sum�:
    //     Cr�e un nouvel objet qui est une copie de l'instance en cours.
    //
    // Retourne�:
    //     Nouvel objet qui est une copie de cette instance.
    Object clone();
}

