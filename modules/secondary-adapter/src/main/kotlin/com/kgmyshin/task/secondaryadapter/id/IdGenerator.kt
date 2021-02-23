package com.kgmyshin.task.secondaryadapter.id

import com.eaio.uuid.UUID
import org.springframework.stereotype.Component

@Component
class IdGenerator {
  fun generate(): UUID = UUID()
}
