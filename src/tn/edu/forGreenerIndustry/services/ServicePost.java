/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.edu.forGreenerIndustry.services;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.edu.forGreenerIndustry.entities.Post;
import tn.edu.forGreenerIndustry.tools.DataSource;
/**
 *
 * @author mila
 */
public class ServicePost implements IService<Post> {
        private final Connection cnx ;
    
    public ServicePost(){
         this.cnx = DataSource.getInstance().getConnection();
    
}

    @Override
    public void ajouter(Post t) {
        try {
            String req = "INSERT INTO `post`(`id_post`,`id_entreprise`, `titre`, `typeDeContenu`, `contenu`, `date`, `image`, `nbr_de_vue`) " +
                         "VALUES (" + t.getId_post() + "," + t.getId_entreprise() + ",'" + t.getTitre() + "','" + t.getTypeDeContenu() + "','" + t.getContenu() + "','" +
                         t.getDate() + "','" + t.getImage() + "'," + t.getNbr_de_vue() + ")";
            
            Statement stm = cnx.createStatement();
            stm.executeUpdate(req);
        }   catch (SQLException ex) { 
                 System.out.println(ex.getMessage());
            } 
    }

    @Override
    public void modifier(Post t) {
        if (t == null) {
            System.out.println("The Post cannot be modified.");
            return;
        }
        try {
            String req = "UPDATE `post` SET `id_post` = ?, `id_entreprise` = ?, `titre` = ?, `typeDeContenu` = ?, `contenu` = ?, "
                    + "`date` = ?, `image` = ?, `nbr_de_vue` = ? WHERE `id_post` = ?" ; 
            
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt (2, t.getId_entreprise());
            pst.setString(3, t.getTitre());
            pst.setString(4, t.getTypeDeContenu());
            pst.setString(5, t.getContenu());
            pst.setDate(6, t.getDate());
            pst.setString(7, t.getImage());
            pst.setFloat(8, t.getNbr_de_vue());
            pst.setInt(1, t.getId_post());

            pst.executeUpdate();
        }   catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        
       
    }

    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `post` WHERE `id_post` = ?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id);
            
            int rowCount = pst.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Post supprimé avec succès");
            } else {
                System.out.println("Aucun post correspondant n'a été trouvé");
            }
        }   catch (SQLException ex) {
            System.err.println(ex.getMessage());
                
 
            }
    }


    @Override
    public Post getOne(int id_post) {
        try {
            String req = "SELECT * FROM `post` WHERE `id_post` = ?";
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setInt(1, id_post);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                Post post = new Post();
                post.setId_post(rs.getInt("id_post"));
                post.setId_entreprise(rs.getInt("id_entreprise"));
                post.setTitre(rs.getString("titre"));
                post.setTypeDeContenu(rs.getString("typeDeContenu"));
                post.setContenu(rs.getString("contenu"));
                post.setDate(rs.getDate("date"));
                post.setImage(rs.getString("image"));
                post.setNbr_de_vue(rs.getFloat("nbr_de_vue"));
                return post;
            } else {
                System.out.println("No post found with id_post = " + id_post);
                return null;
            }
        }   catch (SQLException ex) {
                System.err.println(ex.getMessage());
            return null;
            }
    }

    @Override
    public List<Post> getAll(Post t){
        
        String req = "SELECT * FROM `post`";
        List<Post> postList = new ArrayList();


    try {
        Statement stm = this.cnx.createStatement();
        ResultSet rs = stm.executeQuery(req);

        while (rs.next()) {
            Post post = new Post();
            post.setId_post(rs.getInt("id_post"));
            post.setId_entreprise(rs.getInt("id_entreprise"));
            post.setTitre(rs.getString("titre"));
            post.setTypeDeContenu(rs.getString("typeDeContenu"));
            post.setContenu(rs.getString("contenu"));
            post.setDate(rs.getDate("date"));
            post.setImage(rs.getString("image"));
            post.setNbr_de_vue(rs.getFloat("nbr_de_vue"));
            postList.add(post);
        }
    }       catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
    
    return postList; 
     
}

}


   
    
