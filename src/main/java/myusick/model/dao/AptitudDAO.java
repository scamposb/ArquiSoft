package myusick.model.dao;

import myusick.controller.dto.TagDTO;
import myusick.model.connection.ConnectionAdmin;
import myusick.model.connection.PoolManager;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Cuenta de clase on 02/04/2015.
 */
public class AptitudDAO {

    public ArrayList<String> getAptitudesByPersona(int uuid){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        ArrayList<String> result = new ArrayList<>();
        try {
            String queryString = "select nombre from aptitud where idAptitud in (select idAptitud " +
                    "from tiene_aptitud where uuid_p=?)";
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                result.add(resultSet.getString(1));
            }
            con.commit();
            pool.returnConnection(con);
            return result;
        }catch(Exception e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return null;
        }
    }

    public boolean registrarAptitud(TagDTO td) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            int existeAptitud = getIdByNombre(td.getNombre());
            if (existeAptitud == -1) {
                /*El tag no existe */
                String query = "insert into aptitud (nombre) values (?)";
                PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, td.getNombre());
                int insertedRows = ps.executeUpdate();
                if (insertedRows == 1) {
                    ResultSet keys = ps.getGeneratedKeys();
                    keys.next();
                    int uuid = keys.getInt(1);
                    /* anadimos ahora a la tabla de asociacion con el publicante */
                    con.commit();
                    pool.returnConnection(con);
                    return asociarAptitud(uuid, td.getPublicante());

                } else{
                    con.rollback();
                    pool.returnConnection(con);
                    return false;
                }
            } else if (existeAptitud == -2) {
                /*Error de BD*/
                con.rollback();
                pool.returnConnection(con);
                return false;
            } else {
                /*el tag existe*/
                con.rollback();
                pool.returnConnection(con);
                return asociarAptitud(existeAptitud, td.getPublicante());
            }
        } catch (SQLException e) {
            try{
                con.rollback();
                pool.returnConnection(con);
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }

    public int getIdByNombre(String nombre){
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try{
            String query = "select idAptitud from aptitud where nombre = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                con.commit();
                pool.returnConnection(con);
                return rs.getInt(1);
            }
            else{
                con.rollback();
                pool.returnConnection(con);
                return -1;
            }
        }catch(SQLException e){
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return -2;
        }
    }

    private boolean asociarAptitud(int idAptitud, int idPublicante) {
        PoolManager pool = PoolManager.getInstance();
        Connection con = pool.getConnection();
        try {
            PersonaDAO pdao = new PersonaDAO();
            String query = "insert into tiene_aptitud (uuid_p,idAptitud) values (?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, idPublicante);
            ps.setInt(2, idAptitud);
            int insertedRows = ps.executeUpdate();
            if (insertedRows == 1) {
                con.commit();
                pool.returnConnection(con);
                return true;
            } else {
                con.rollback();
                pool.returnConnection(con);
                return false;
            }
        } catch (SQLException e) {
            try{
                con.rollback();
            }catch(SQLException e2){e2.printStackTrace();}
            e.printStackTrace();
            pool.returnConnection(con);
            return false;
        }
    }
}
