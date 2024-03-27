/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DAO;

import java.util.ArrayList;

/**
 *
 * @author lethi
 */
public interface DAO<E> {

    public ArrayList<E> getArrayListAll();

    public E getObjectById(String x);

    public int UpdateSQL(E object);

    public int InsertSQL(E o);

}
