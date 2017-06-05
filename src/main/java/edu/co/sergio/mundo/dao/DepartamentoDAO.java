package edu.co.sergio.mundo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import edu.co.sergio.mundo.vo.Proyecto;
import edu.co.sergio.mundo.vo.*;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Isabel-Fabian
 * @since 12/08/2015
 * @version 2 Clase que permite la gestion de la tabla Depto en la base de
 * datos.
 *
 * CREATE TABLE Depto( id_depto integer, nom_depto varchar(40), PRIMARY
 * KEY(id_depto) );
 */
public class DepartamentoDAO implements IBaseDatos<Departamento> {

    /**
     * Funcion que permite obtener una lista de los departamentos existentes en
     * la base de datos
     *
     * @return List<Departamento> Retorna la lista de Departamentos existentes
     * en la base de datos
     */
    public List<Departamento> findAll() {
        List<Departamento> departamentos = null;
        String query = "SELECT * FROM Depto";
        Connection connection = null;
        try {
            connection = Conexion.getConnection();
        } catch (URISyntaxException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            int id = 0;
            String nombre = null;

            while (rs.next()) {
                if (departamentos == null) {
                    departamentos = new ArrayList<Departamento>();
                }

                Departamento registro = new Departamento();
                id = rs.getInt("id_depto");
                registro.setId_departamento(id);

                nombre = rs.getString("nom_depto");
                registro.setNom_departamento(nombre);

                departamentos.add(registro);
            }
            st.close();

        } catch (SQLException e) {
            System.out.println("Problemas al obtener la lista de Departamentos");
            e.printStackTrace();
        }

        return departamentos;
    }

    /**
     * Funcion que permite realizar la insercion de un nuevo registro en la
     * tabla Departamento
     *
     * @param Departamento recibe un objeto de tipo Departamento
     * @return boolean retorna true si la operacion de insercion es exitosa.
     */
    public boolean insert(Departamento t) {
        boolean result = false;
        Connection connection = null;
        try {
            connection = Conexion.getConnection();
        } catch (URISyntaxException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = " insert into Depto (id_depto,nom_depto)" + " values (?,?)";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, t.getId_departamento());
            preparedStmt.setString(2, t.getNom_departamento());
            result = preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Funcion que permite realizar la actualizacion de un nuevo registro en la
     * tabla Departamento
     *
     * @param Departamento recibe un objeto de tipo Departamento
     * @return boolean retorna true si la operacion de actualizacion es exitosa.
     */
    public boolean update(Departamento t) {
        boolean result = false;
        Connection connection = null;
        try {
            connection = Conexion.getConnection();
        } catch (URISyntaxException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "update Depto set nom_depto = ? where id_depto = ?";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, t.getNom_departamento());
            preparedStmt.setInt(2, t.getId_departamento());
            if (preparedStmt.executeUpdate() > 0) {
                result = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Funcion que permite realizar la eliminario de registro en la tabla
     * Departamento
     *
     * @param Departamento recibe un objeto de tipo Departamento
     * @return boolean retorna true si la operacion de borrado es exitosa.
     */
    public boolean delete(Departamento t) {
        boolean result = false;
        Connection connection = null;
        try {
            connection = Conexion.getConnection();
        } catch (URISyntaxException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "delete from Depto where id_depto = ?";
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setInt(1, t.getId_departamento());
            result = preparedStmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public List<Proyecto> recursos() {
        List<Proyecto> proyectos = null;

        String query = "select nom_proy,Count(id_rec) as total from Proyecto left join Recurso using (id_proyecto) group by nom_proy;";
        Connection connection = null;
        Proyecto d = null;
        try {
            connection = Conexion.getConnection();
        } catch (URISyntaxException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            int total = 0;
            String nombre_pro = null;
            d = new Proyecto();
            while (rs.next()) {
                if (proyectos == null) {
                    proyectos = new ArrayList<Proyecto>();
                }

                nombre_pro = rs.getString("nom_proy");
                d.setName_proy(nombre_pro);

                total = rs.getInt("total");
                d.setTotal(total);

                proyectos.add(d);
            }
            st.close();

        } catch (SQLException e) {
            System.out.println("Problemas al obtener la lista de proyectos");
            e.printStackTrace();
        }
        return proyectos;
    }

    public List<Sensores> service() {
        List<Sensores> servicios = null;

        String query = "select avg(cantidad_milimetros) as total,avg(temperatura) as total2,avg(nivel_Carga) as total3 from sensores group by cantidad_milimetros;";
        Connection connection = null;
        Sensores d = null;
        try {
            connection = Conexion.getConnection();
        } catch (URISyntaxException ex) {
            Logger.getLogger(DepartamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);

            int total = 0;
            int total2 = 0;
            int total3 = 0;
            int cantidad_milimetros = 0;
            int tempe = 0;
            int nivelCarga = 0;
            d = new Sensores();
            while (rs.next()) {
                if (servicios == null) {
                    servicios = new ArrayList<Sensores>();
                }

                cantidad_milimetros = rs.getInt("cantidad_milimetros");
                d.setCantidad_milime(cantidad_milimetros);

                total = rs.getInt("total");
                d.setTotal1(total);

                tempe = rs.getInt("temperatura");
                d.setTemperatura(tempe);

                nivelCarga = rs.getInt("nivel_Carga");
                d.setNivelCarga(nivelCarga);

                total2 = rs.getInt("total2");
                d.setTotal2(total2);

                total3 = rs.getInt("total3");
                d.setTotal3(total3);

                servicios.add(d);
            }
            st.close();

        } catch (SQLException e) {
            System.out.println("Problemas al obtener la lista de sesores");
            e.printStackTrace();
        }
        return servicios;
    }

}
