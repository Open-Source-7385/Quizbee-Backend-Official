package pe.edu.upc.quizbee.iam.domain.model.commands;

import pe.edu.upc.quizbee.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String email, String password, List<Role> roles) {
}