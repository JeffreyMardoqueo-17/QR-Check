package control.asistencia.QRCheck.controlers;

import control.asistencia.QRCheck.models.Trabajador;
import control.asistencia.QRCheck.services.iterfaces.ITrabajadorService;
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
@RequestMapping("/trabajadores")
public class TrabajadorController {

    @Autowired
    private ITrabajadorService trabajadorServices;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1; // si no está seteado se asigna 0
        int pageSize = size.orElse(5); // tamaño de la página, se asigna 5
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<Trabajador> trabajadores = trabajadorServices.buscarTodosPaginados(pageable);
        model.addAttribute("trabajadores", trabajadores);

        int totalPages = trabajadores.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "trabajador/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("trabajador", new Trabajador()); // Añadir un nuevo objeto Trabajador al modelo
        return "trabajador/create"; // Retorna la vista para crear un nuevo trabajador
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Trabajador trabajador, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("trabajador", trabajador); // Asegúrate de agregar el objeto al modelo si hay errores
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "redirect:/trabajadores/create"; // Redirige de nuevo a la vista de creación con mensajes flash
        }

        trabajadorServices.crearOEditOne(trabajador); // Llama al servicio para guardar el trabajador
        attributes.addFlashAttribute("msg", "Trabajador registrado correctamente.");
        return "redirect:/trabajadores"; // Redirige a la lista de trabajadores después de guardar
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Optional<Trabajador> trabajadorOpt = trabajadorServices.buscarPorId(id);

        if (trabajadorOpt.isPresent()) {
            Trabajador trabajador = trabajadorOpt.get();
            model.addAttribute("trabajador", trabajador);
            return "trabajador/details";
        } else {
            // Manejo del caso cuando el trabajador no se encuentra
            return "error";
        }
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Trabajador trabajador = trabajadorServices.buscarPorId(id).get();
        model.addAttribute("trabajador", trabajador);
        return "trabajador/edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Trabajador trabajador, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("trabajador", trabajador);
            attributes.addFlashAttribute("error", "No se pudo actualizar debido a un error.");
            return "redirect:/trabajadores/edit/" + trabajador.getId(); // Redirige de nuevo a la vista de edición con mensajes flash
        }

        trabajadorServices.crearOEditOne(trabajador); // Llama al servicio para actualizar el trabajador
        attributes.addFlashAttribute("msg", "Trabajador actualizado correctamente.");
        return "redirect:/trabajadores"; // Redirige a la lista de trabajadores después de actualizar
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Trabajador trabajador = trabajadorServices.buscarPorId(id).get();
        model.addAttribute("trabajador", trabajador);
        return "trabajador/delete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id, RedirectAttributes attributes) {
        trabajadorServices.eliminarPorId(id);
        attributes.addFlashAttribute("msg", "Trabajador eliminado correctamente.");
        return "redirect:/trabajadores"; // Redirige a la lista de trabajadores después de eliminar
    }
}