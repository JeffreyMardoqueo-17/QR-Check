package control.asistencia.QRCheck.controlers;

import control.asistencia.QRCheck.models.Roles;
import control.asistencia.QRCheck.services.iterfaces.IRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    private IRolesService rolesService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Roles> roles = rolesService.buscarTodosPaginados(pageable);
        model.addAttribute("roles", roles);

        int totalPages = roles.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "roles/index";
    }

    @GetMapping("/create")
    public String create(Roles roles){
        return "roles/create";
    }

    @PostMapping("/save")
    public String save(Roles roles, BindingResult result, Model model, RedirectAttributes attributes){
        if(result.hasErrors()){
            model.addAttribute(roles);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "roles/create";
        }

        rolesService.crearOEditar(roles);
        attributes.addFlashAttribute("msg", "Rol creado correctamente");
        return "redirect:/roles";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Roles roles = rolesService.buscarPorId(id).get();
        model.addAttribute("roles", roles);
        return "roles/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model){
        Roles roles = rolesService.buscarPorId(id).get();
        model.addAttribute("roles", roles);
        return "roles/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        Roles roles = rolesService.buscarPorId(id).get();
        model.addAttribute("roles", roles);
        return "roles/delete";
    }

    @PostMapping("/delete")
    public String delete(Roles roles, RedirectAttributes attributes){
        rolesService.eliminarPorId(roles.getId());
        attributes.addFlashAttribute("msg", "Rol eliminado correctamente");
        return "redirect:/roles";
    }

}
