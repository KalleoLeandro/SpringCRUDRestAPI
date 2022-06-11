/**
 * 
 */
package br.com.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.model.Usuario;
import br.com.springboot.repository.UsuarioRepository;

/**
 * @author Kalleo
 *
 */

@RestController
public class UsuarioController {
	
	@Autowired
	public UsuarioRepository usuarioRepository;
	
	
	
	@RequestMapping(value="/olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome){
		Usuario user = new Usuario();
		user.setNome(nome);
		user.setIdade(33);
		
		usuarioRepository.save(user);
		
		return "Olá Mundo : " + nome;
	}	
	
	@GetMapping(value="/buscartodos")
	@ResponseBody //Retorna os dados para o corpo da resposta
	public ResponseEntity<List<Usuario>> listaUsuarios(){
		
		List<Usuario> usuarios = usuarioRepository.findAll();
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);		
	}
	
	@PostMapping(value="/gravarusuario")
	@ResponseBody
	public ResponseEntity<String> gravarUsuario(@RequestBody Usuario usuario){
		Usuario user = new Usuario();
		user.setNome(usuario.getNome());
		user.setIdade(usuario.getIdade());
		user = usuarioRepository.save(user);		
		return new ResponseEntity<String>("Usuário criado com sucesso!!!", HttpStatus.CREATED);	
	}
	
	@DeleteMapping(value="/deletarusuario")
	public ResponseEntity<String> deletarUsuario(@RequestBody Long id){
		usuarioRepository.deleteById(id);
		return new ResponseEntity<String>("Deletado com sucesso!!!", HttpStatus.OK);
	}
	
	@GetMapping(value="/buscaruserid")
	@ResponseBody
	public ResponseEntity<Usuario> buscarUsuarioPorId(@RequestParam (name="id") Long id){
		Usuario user = usuarioRepository.findById(id).get();
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}
	
	@PutMapping(value="/atualizarusuario")
	@ResponseBody
	public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario){
		Usuario user = new Usuario();
		user.setId(usuario.getId());
		user.setNome(usuario.getNome());
		user.setIdade(usuario.getIdade());
		user = usuarioRepository.saveAndFlush(user);		
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);	
	}
	
	@GetMapping(value="/buscarpornome")
	@ResponseBody
	public ResponseEntity<List<Usuario>> buscarUsuarioPorNome(@RequestParam (name="nome") String nome){
		List<Usuario> usuarios = usuarioRepository.buscarPorNome(nome.trim());		
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.FOUND);
	}
	
	
}
