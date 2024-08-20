package control.asistencia.QRCheck.controlers;

import control.asistencia.QRCheck.models.Empresa;
import control.asistencia.QRCheck.services.iterfaces.IEmpresaService;
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
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private IEmpresaService empresaService;

    // Este método maneja la pantalla principal donde se listan todas las empresas.
    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        // Pagina la lista de empresas, mostrando 5 por defecto.
        int currentPage = page.orElse(1) - 1; // Si no se elige la página, se va a la primera.
        int pageSize = size.orElse(5); // Si no se elige el tamaño, se muestran 5 empresas.
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        // Busca todas las empresas de forma paginada.
        Page<Empresa> empresas = empresaService.buscarTodosPaginados(pageable);
        model.addAttribute("empresas", empresas);

        // Calcula el total de páginas y las muestra.
        int totalPages = empresas.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "empresa/index";
    }

    // Este método muestra el formulario para crear una nueva empresa.
    @GetMapping("/create")
    public String create(Empresa empresa) {
        return "empresa/create";
    }

    // Este método guarda una nueva empresa después de que el usuario llena el formulario.
    @PostMapping("/save")
    public String save(Empresa empresa, BindingResult result, Model model, RedirectAttributes attributes) {
        // Si hay algún error en el formulario, no guarda y regresa al formulario.
        if (result.hasErrors()) {
            model.addAttribute(empresa);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "empresa/create";
        }

        // Guarda o edita la empresa y muestra un mensaje de éxito.
        empresaService.crearOEditar(empresa);
        attributes.addFlashAttribute("msg", "Empresa creada correctamente");
        return "redirect:/empresa";
    }

    // Este método muestra los detalles de una empresa específica.
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Empresa empresa = empresaService.buscarPorId(id).orElse(null);
        model.addAttribute("empresa", empresa);
        return "empresa/details";
    }

    // Este método muestra el formulario para editar una empresa existente.
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Empresa empresa = empresaService.buscarPorId(id).orElse(null);
        model.addAttribute("empresa", empresa);
        return "empresa/edit";
    }

    // Este método muestra una pantalla para confirmar la eliminación de una empresa.
    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Empresa empresa = empresaService.buscarPorId(id).orElse(null);
        model.addAttribute("empresa", empresa);
        return "empresa/delete";
    }

    // Este método elimina la empresa después de confirmar la eliminación.
    @PostMapping("/delete")
    public String delete(Empresa empresa, RedirectAttributes attributes) {
        empresaService.eliminarPorId(empresa.getId());
        attributes.addFlashAttribute("msg", "Empresa eliminada correctamente");
        return "redirect:/empresa";
    }
}
