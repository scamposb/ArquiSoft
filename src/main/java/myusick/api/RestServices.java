package myusick.api;

import myusick.api.services.*;
import myusick.model.dto.GroupDTO;
import myusick.model.dto.PublicationsDTO;
import myusick.model.dto.RegisterDTO;
import myusick.model.dto.TagDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

//import org.glassfish.jersey.media.sse.EventOutput;
//import org.glassfish.jersey.media.sse.SseFeature;

/**
 * REST backend
 * Backend del servidor
 * Todos los endpoints de la API se encontrarán aquí
 */
@Path("/")
public class RestServices {

    /**
     * Lista de servicios
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String listServices(){
        return APIdirectory.listServices();
    }

    /**
     * Autentica a un usuario
     * @param user
     * @param password
     * @return
     */
    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String authenticate(@FormParam("user") String user, @FormParam("password") String password){
        return LoginService.authenticate(user, password);
    }

    /**
     * Registra a un usuario
     * @param info
     * @param registerDTO
     * @return
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String register(@Context UriInfo info, RegisterDTO registerDTO){
        return RegisterService.register(info, registerDTO);
    }

    /**
     * Saca los datos de perfil del usuario {userid}
     * @param userid
     * @return
     */
    @GET
    @Path("/profile/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String userProfile(@PathParam("userid") int userid){
        return ProfileService.profile(userid);
    }

    /**
     * Crea un grupo para un usuario
     * @param info
     * @param groupDTO
     * @return
     */
    @POST
    @Path("/newgroup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String newGroup(@Context UriInfo info, GroupDTO groupDTO){
        System.out.println("AQUI LLEGA 1");
        return NewGroupService.newGroup(info, groupDTO);
    }

    /**
     * Crea y/o añade un tag a un usuario 
     * @param info
     * @param tagDTO
     * @return
     */
    @POST
    @Path("/newtag")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String newTag(@Context UriInfo info, TagDTO tagDTO){
        return TagService.newTag(info, tagDTO);
    }

    /**
     * Crea un post para un usuario
     * @param info
     * @param postDTO
     * @return
     */
    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String newPost(@Context UriInfo info, PublicationsDTO postDTO){
        return PostService.newPost(info, postDTO);
    }

    /**
     * Busca el comentario cuyo id es {id}
     * @param id
     * @return
     */
    @GET
    @Path("/post/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getPost(@PathParam("id") int id){
        return PostService.getPost(id);
    }

    /**
     * El usuario {seguidor} sigue a {seguido}
     * @param seguidor
     * @param seguido
     * @return
     */
    @GET
    @Path("/follow/{seguidor}/{seguido}")
    @Produces(MediaType.APPLICATION_JSON)
    public String follow(@PathParam("seguidor") int seguidor, @PathParam("seguido") int seguido){
        return FollowService.follow(seguidor,seguido);
    }

    /**
     * El usuario {seguidor} deja de sseguir a {seguido}
     * @param seguidor
     * @param seguido
     * @return
     */
    @GET
    @Path("/unfollow/{seguidor}/{seguido}")
    @Produces(MediaType.APPLICATION_JSON)
    public String unfollow(@PathParam("seguidor") int seguidor, @PathParam("seguido") int seguido){
        return FollowService.unfollow(seguidor, seguido);
    }

    /**
     * Comprueba si el usuario {seguidor} sigue a {seguido}
     * @param seguidor
     * @param seguido
     * @return
     */
    @GET
    @Path("/isfollowing/{seguidor}/{seguido}")
    @Produces(MediaType.APPLICATION_JSON)
    public String isFollowing(@PathParam("seguidor") int seguidor, @PathParam("seguido") int seguido){
        return FollowService.isFollowing(seguidor, seguido);
    }

    /**
     * Agrega un nuevo miembro pendiente de aceptacion a la banda
     * @param bandid
     * @param personid
     * @return
     */
    @GET
    @Path("/band/apply/{bandid}/{personid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String applyForGroup(@PathParam("bandid") int bandid, @PathParam("personid") int personid){
        return BandService.apply(bandid,personid);
    }

    /**
     * Elimina un miembro existente de la banda
     * @param bandid
     * @param personid
     * @return
     */
    @GET
    @Path("/band/leave/{bandid}/{personid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String leaveGroup(@PathParam("bandid") int bandid, @PathParam("personid") int personid) {
        return BandService.leave(bandid,personid);
    }

    /**
     * Acepta un nuevo miembro en la banda
     * @param bandid
     * @param personid
     * @return
     */
    @GET
    @Path("/band/accept/{bandid}/{personid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String acceptFromGroup(@PathParam("bandid") int bandid, @PathParam("personid") int personid){
        return BandService.accept(bandid,personid);
    }

    /**
     * Rechaza un nuevo miembro en la banda
     * @param bandid
     * @param personid
     * @return
     */
    @GET
    @Path("/band/reject/{bandid}/{personid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String rejectIntoGroup(@PathParam("bandid") int bandid, @PathParam("personid") int personid){
        return BandService.reject(bandid,personid);
    }

    /**
     * Busca los miembros pendientes de aceptación de la banda cuyo id es {bandid}
     * @param bandid
     * @return
     */
    @GET
    @Path("/band/applicants/{bandid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getApplicants(@PathParam("bandid") int bandid){
        return BandService.getApplicants(bandid);
    }

    /**
     * Busca los grupos del usuario cuyo id es {userid}
     * @param userid
     * @return
     */
    @GET
    @Path("/groups/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getGroups(@PathParam("userid") int userid){
        return ProfileService.getGroups(userid);
    }

    /**
     * Busca los últimos mensajes de los publicantes a los cuales está suscrito el usuario cuyo id es {userid}
     * @param userid
     * @return
     */
    @GET
    @Path("/last/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLatestPosts(@PathParam("userid") int userid){
        return PostService.getLatestPosts(userid);
    }
    
}