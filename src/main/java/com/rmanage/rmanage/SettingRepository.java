package com.rmanage.rmanage;

import com.rmanage.rmanage.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {
}
