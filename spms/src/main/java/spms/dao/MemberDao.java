package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import spms.vo.Member;

public class MemberDao {
	Connection conn;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	int memberCount = 0;
	Member member;	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	//��� ����Ʈ ��� �޼���
	public List<Member> selectList() throws Exception {
		Statement stmt = null;
		ArrayList<Member> members;
		try{
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(
					"SELECT MNO,MNAME,EMAIL,CRE_DATE" + 
					" FROM MEMBERS" +
					" ORDER BY MNO ASC");
			
			
			members = new ArrayList<Member>();
			
			// �����ͺ��̽����� ȸ�� ������ ������ Member�� ��´�.
			// �׸��� Member��ü�� ArrayList�� �߰��Ѵ�.
			while(rs.next()) {
				members.add(new Member()
							.setNo(rs.getInt("MNO"))
							.setName(rs.getString("MNAME"))
							.setEmail(rs.getString("EMAIL"))
							.setCreatedDate(rs.getDate("CRE_DATE"))	);
			}
		}catch(Exception e){
			throw e;
		} finally{
			try {if (rs != null) rs.close();} catch(Exception e) {}
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
		}
		return members;
	}
	
	//ȸ������ ó�� �޼���
	public int insert(Member member) throws Exception{				
		try{
			stmt = conn.prepareStatement(
					"INSERT INTO MEMBERS(EMAIL,PWD,MNAME,CRE_DATE,MOD_DATE)"
					+ " VALUES (?,?,?,NOW(),NOW())");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			memberCount = stmt.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}finally{
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
		}
		return memberCount;
	}
	
	//ȸ������ ó�� �޼���
	public int delete(int no) throws Exception{
		try{
			stmt = conn.prepareStatement("DELETE FROM MEMBERS WHERE MNO=?");
			stmt.setInt(1, no);
			memberCount = stmt.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}finally{
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
		}
		return memberCount;
	}
	
	//ȸ�������� ��ȸ �޼���
	public Member selectOne(int no) throws Exception{
			try{
				stmt = conn.prepareStatement("SELECT MNO,EMAIL,MNAME,CRE_DATE FROM MEMBERS WHERE MNO=?");
				stmt.setInt(1, no);
				rs = stmt.executeQuery();	
					if (rs.next()) {
						member = new Member();
						member.setNo(rs.getInt("MNO"));
						member.setEmail(rs.getString("EMAIL"));
						member.setName(rs.getString("MNAME"));
						member.setCreatedDate(rs.getDate("CRE_DATE"));
						
					} else {
						throw new Exception("�ش� ��ȣ�� ȸ���� ã�� �� �����ϴ�.");
					}
				
			}catch(Exception e){
				throw e;
			}finally{
				try {if (rs != null) rs.close();} catch(Exception e) {}
				try {if (stmt != null) stmt.close();} catch(Exception e) {}
			}
		return member;	
	}
	
	public int update(Member member) throws Exception{
		
		try{
			stmt = conn.prepareStatement(
					"UPDATE MEMBERS SET EMAIL=?,MNAME=?,MOD_DATE=now()"
					+ " WHERE MNO=?");
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			memberCount = stmt.executeUpdate();
			
		}catch(Exception e){
			throw e;
		}finally{
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
		}
		return memberCount;
	}
	
	public Member exist(String email, String password)throws Exception{
		
		try{
			stmt = conn.prepareStatement(
					"SELECT MNAME,EMAIL FROM MEMBERS"
					+ " WHERE EMAIL=? AND PWD=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				member = new Member()
					.setEmail(rs.getString("EMAIL"))
					.setName(rs.getString("MNAME"));
				return member;
			}else{
				return null;
			}
		}catch(Exception e){
			throw e;
		}finally{
			
		}
		
	}
}
