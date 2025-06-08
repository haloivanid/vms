package com.ivanazis.vms.modules.vacancy.domain.port.out;

import com.ivanazis.vms.modules.vacancy.domain.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Port (kontrak) untuk operasi persistence Vacancy.
 * Lapisan domain bergantung pada interface ini, bukan pada implementasi
 * database.
 */
public interface VacancyRepositoryPort {

  /**
   * Menyimpan atau memperbarui lowongan.
   * 
   * @param vacancy Objek lowongan untuk disimpan.
   * @return Objek lowongan yang telah disimpan.
   */
  Vacancy save(Vacancy vacancy);

  /**
   * Menemukan semua lowongan dengan paginasi.
   * 
   * @param pageable Informasi paginasi (halaman, ukuran, urutan).
   * @return Halaman (Page) yang berisi daftar lowongan.
   */
  Page<Vacancy> findAll(Pageable pageable);

  /**
   * Menemukan lowongan berdasarkan ID-nya.
   * 
   * @param id ID dari lowongan yang dicari.
   * @return Optional yang berisi lowongan jika ditemukan.
   */
  Optional<Vacancy> findById(String id);

  /**
   * Menghapus lowongan berdasarkan ID-nya.
   * 
   * @param id ID dari lowongan yang akan dihapus.
   */
  void deleteById(String id);
}
