package pe.edu.upc.quizbee.iam.infrastructure.hashing.bcrypt;

import pe.edu.upc.quizbee.iam.application.internal.outboundservices.hashing.HashingService;
import pe.edu.upc.quizbee.iam.infrastructure.hashing.bcrypt.services.HashingServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This interface is a marker interface for the BCrypt hashing service.
 * It extends the {@link HashingService} and {@link PasswordEncoder} interfaces.
 * This interface is used to inject the BCrypt hashing service in the {@link HashingServiceImpl} class.
 */
public interface BCryptHashingService extends HashingService, PasswordEncoder {
}