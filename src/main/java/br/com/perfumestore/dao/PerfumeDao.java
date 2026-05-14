package br.com.perfumestore.dao;

import br.com.perfumestore.dto.PerfumeDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PerfumeDao {

    private final JdbcTemplate jdbc;

    public PerfumeDao(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<PerfumeDTO> rowMapper = new RowMapper<>() {

        @Override
        public PerfumeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            PerfumeDTO dto = new PerfumeDTO();
            dto.setId(rs.getString("id"));
            dto.setName(rs.getString("name"));
            dto.setBrand(rs.getString("brand"));
            dto.setFragranceFamily(rs.getString("fragrance_family"));
            dto.setVolume(rs.getString("volume"));
            dto.setPrice(rs.getDouble("price"));
            return dto;
        }

    };

    // SELECT * FROM perfume -> List<PerfumeDTO>
    public List<PerfumeDTO> findAll() {

        String sql = "SELECT * FROM perfume";

        return jdbc.query(sql, rowMapper);

    }

    // INSERT INTO perfume
    public void save(PerfumeDTO perfumeDTO) {

        String sql = "INSERT INTO perfume (name, brand, fragrance_family, volume, price) VALUES (?, ?, ?, ?, ?)";

        jdbc.update(sql, perfumeDTO.getName(), perfumeDTO.getBrand(), perfumeDTO.getFragranceFamily(), perfumeDTO.getVolume(), perfumeDTO.getPrice());

    }

    // DELETE FROM perfume WHERE id = ?
    public void deleteById(String id) {

        String sql = "DELETE FROM perfume WHERE id = ?";

        jdbc.update(sql, Long.valueOf(id));

    }

    // UPDATE perfume SET ... WHERE id = ?
    public void update(String id, PerfumeDTO perfumeDTO) {

        String sql = "UPDATE perfume SET name = ?, brand = ?, fragrance_family = ?, volume = ?, price = ? WHERE id = ?";

        jdbc.update(sql, perfumeDTO.getName(), perfumeDTO.getBrand(), perfumeDTO.getFragranceFamily(), perfumeDTO.getVolume(), perfumeDTO.getPrice(), Long.valueOf(id));

    }

    public PerfumeDTO findById(String id) {

        String sql = "SELECT * FROM perfume WHERE id = ?";

        return jdbc.queryForObject(sql, rowMapper, Long.valueOf(id));

    }

}
