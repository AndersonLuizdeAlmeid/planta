package pkBanco;


import pkModelo.Planta;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlantaDAO 
{
    private String urlBanco = "jdbc:postgresql://localhost:5432/bancoemocionante";
    private String usuarioBanco = "postgres";
    private String senhaBanco = "mcjow1998";
    private Connection getConnection() 
    {
        Connection connection = null;
        try 
        {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(urlBanco, usuarioBanco, senhaBanco);
        } catch (SQLException e) 
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        return connection;
    }
    public int cadastrarPlanta(Planta planta)
    {
        int cadastrado = 0;
        try
        {
            Connection conexao = getConnection();
            if (conexao != null)
            {
                PreparedStatement cadastrar = conexao.prepareStatement("INSERT INTO plantas(id,nome,descricao,valor) VALUES (?,?,?,?)");
                cadastrar.setInt(1, pegarId(planta));
                cadastrar.setString(1, planta.getNome());
                cadastrar.setString(2, planta.getDescricao());
                cadastrar.setDouble(3, planta.getValor());
                cadastrar.executeUpdate();
                cadastrado = 1;
                cadastrar.close();
                conexao.close();
            }	
        }catch (SQLException e)
        {
            e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return cadastrado;
    }
    public List<Planta> selecionarPlanta(String nome) 
    {
        List<Planta> plantas=null;
        try
        {
            Connection conexao = getConnection();
            if (conexao != null)
            {
                PreparedStatement consultar = conexao.prepareStatement("SELECT * FROM plantas WHERE nome = '" + nome + "'");
                ResultSet rs = consultar.executeQuery();
                while (rs.next()) 
                {
                    Integer id = rs.getInt("id");
                    nome = rs.getString("nome");
                    String descricao = rs.getString("descricao");
                    Double valor = rs.getDouble("valor");
                    var planta = new Planta(id,nome,descricao,valor);
                    plantas.add(planta);
                }
                rs.close();
                consultar.close();
                conexao.close();
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return plantas;
    }
    
    public List<Planta> consultarPlantas(String campo,String dado) {
	List<Planta> plantas = new ArrayList<>();
	try
	{
            Connection conexao = getConnection();
            if (conexao != null)
            {
		PreparedStatement consultarPlantas;
		if (!campo.equals("descricao"))
                    consultarPlantas = conexao.prepareStatement("SELECT * FROM plantas WHERE " + campo + " LIKE '%" + dado + "%'");
		else
                    consultarPlantas = conexao.prepareStatement("SELECT * FROM plantas WHERE descricao = " + dado);
                ResultSet rs = consultarPlantas.executeQuery();
                while (rs.next()) 
                {
                    Integer id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String descricao = rs.getString("descricao");
                    Double valor = rs.getDouble("valor");
                    plantas.add(new Planta(id,nome,descricao,valor));
                }	
                rs.close();
                consultarPlantas.close();
                conexao.close();
            }
	}catch (SQLException e)
	{
            e.printStackTrace();
	}catch (Exception e)
	{
            e.printStackTrace();
        }
	return plantas;
    }
    
    
    public boolean deletarPlanta(String nome)
    {
	boolean clienteDeletado=false;
	try
	{
            Connection conexao = getConnection();
            if (conexao != null)
            {
		PreparedStatement deletar = conexao.prepareStatement("DELETE FROM clientes WHERE cpf = '" + cpf + "'");
		clienteDeletado = deletar.executeUpdate() > 0;
		deletar.close();
		conexao.close();
            }
        }catch (SQLException e)
	{
            e.printStackTrace();
	}catch (Exception e)
	{
            e.printStackTrace();
	}
	return clienteDeletado;
    }
    public boolean atualizarCliente(Planta cliente)
    {
	boolean clienteAtualizado=false;
	try
	{
            Connection conexao = getConnection();
            if (conexao != null)
            {
		PreparedStatement atualizar = conexao.prepareStatement("UPDATE clientes set nome=?,idade=?,telefone=?,email=? where cpf=?");
		atualizar.setString(1,cliente.getNome());
		atualizar.setInt(2,cliente.getIdade());
		atualizar.setString(3,cliente.getTelefone());
		atualizar.setString(4,cliente.getEmail());
		atualizar.setString(5,cliente.getCpf());
		clienteAtualizado = atualizar.executeUpdate() > 0;
		atualizar.close();
		conexao.close();
            }
        }catch (SQLException e)
	{
            e.printStackTrace();
	}catch (Exception e)
	{
            e.printStackTrace();
	}
	return clienteAtualizado;
    }
    
    public Integer pegarId(Planta planta){
        
        try
	{
            Connection conexao = getConnection();
            if (conexao != null)
            {
		PreparedStatement pegarId = conexao.prepareStatement("SELECT MAX(id) FROM plantas");
		pegarId.setInt(1,planta.getId());
		conexao.close();
            }
        }catch (SQLException e)
	{
            e.printStackTrace();
	}catch (Exception e)
	{
            e.printStackTrace();
	}

        return planta.getId() + 1;
    }
}
