package control.asistencia.QRCheck.controlers;

import control.asistencia.QRCheck.models.Departamento;
import control.asistencia.QRCheck.models.Roles;
import control.asistencia.QRCheck.services.iterfaces.IDepartamentoService;
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
@RequestMapping("/departamentos")
public class DepartamentoController {

    @Autowired
    private IDepartamentoService departamentoService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Departamento> departamentos = departamentoService.buscarTodosPaginados(pageable);
        model.addAttribute("departamentos", departamentos);

        int totalPages = departamentos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "departamentos/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("departamento", new Departamento()); // Añadido para crear un nuevo objeto
        return "departamentos/create";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("departamento") Departamento departamento, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("departamento", departamento);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "departamentos/create";
        }

        departamentoService.crearOEditar(departamento);
        attributes.addFlashAttribute("msg", "Departamento creado correctamente");
        return "redirect:/departamentos";
    }


    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Departamento departamentos = departamentoService.buscarPorId(id).get();
        model.addAttribute("departamentos", departamentos);
        return "departamentos/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Optional<Departamento> departamentoOpt = departamentoService.buscarPorId(id);
        if (departamentoOpt.isPresent()) {
            model.addAttribute("departamento", departamentoOpt.get());
            return "departamentos/edit";
        } else {
            return "error/404";
        }
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model){
        Departamento departamento = departamentoService.buscarPorId(id).get();
        model.addAttribute("departamentos", departamento);
        return "departamentos/delete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id, RedirectAttributes attributes) {
        departamentoService.eliminarPorId(id);
        attributes.addFlashAttribute("msg", "Departamento eliminado correctamente");
        return "redirect:/departamentos";
    }
}
